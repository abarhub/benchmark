<#
.SYNOPSIS
    Execute un programme et affiche des statistiques d'execution.

.PARAMETER Executable
    Le programme a executer.

.PARAMETER Arguments
    Les arguments a passer au programme (optionnels).

.EXAMPLE
    .\Measure-Exec.ps1 ping "-n 3 localhost"
    .\Measure-Exec.ps1 java "-version"
    .\Measure-Exec.ps1 python "script.py --input data.csv"
#>

[CmdletBinding()]
param (
    [Parameter(Mandatory=$true, Position=0)]
    [string]$Executable,

    [Parameter(Mandatory=$false, Position=1)]
    [string]$Arguments = ""
)

function Write-Header {
    param([string]$Text)
    $line = "-" * 60
    Write-Host ""
    Write-Host $line -ForegroundColor DarkGray
    Write-Host "  $Text" -ForegroundColor Cyan
    Write-Host $line -ForegroundColor DarkGray
}

function Write-Stat {
    param([string]$Label, [string]$Value, [ConsoleColor]$Color = "White")
    $paddedLabel = $Label.PadRight(32)
    Write-Host "  $paddedLabel" -NoNewline -ForegroundColor DarkGray
    Write-Host $Value -ForegroundColor $Color
}

function Format-Bytes {
    param([long]$Bytes)
    if ($Bytes -ge 1GB) { return "{0:N2} GB" -f ($Bytes / 1GB) }
    if ($Bytes -ge 1MB) { return "{0:N2} MB" -f ($Bytes / 1MB) }
    if ($Bytes -ge 1KB) { return "{0:N2} KB" -f ($Bytes / 1KB) }
    return "$Bytes B"
}

function Format-TimeSpan {
    param([TimeSpan]$ts)
    if ($ts.TotalHours -ge 1) {
        return "{0}h {1}m {2:N3}s" -f [int]$ts.TotalHours, $ts.Minutes, ($ts.Seconds + $ts.Milliseconds / 1000.0)
    }
    if ($ts.TotalMinutes -ge 1) {
        return "{0}m {1:N3}s" -f $ts.Minutes, ($ts.Seconds + $ts.Milliseconds / 1000.0)
    }
    return "{0:N3}s" -f $ts.TotalSeconds
}

# Resolution de l'executable
$resolvedExe = Get-Command $Executable -ErrorAction SilentlyContinue
if (-not $resolvedExe) {
    Write-Error "Impossible de trouver l'executable : '$Executable'"
    exit 1
}
$fullPath = $resolvedExe.Source

# Preparation du processus
$psi = New-Object System.Diagnostics.ProcessStartInfo
$psi.FileName               = $fullPath
$psi.Arguments              = $Arguments
$psi.UseShellExecute        = $false
$psi.RedirectStandardOutput = $true
$psi.RedirectStandardError  = $true
$psi.CreateNoWindow         = $false

$process = New-Object System.Diagnostics.Process
$process.StartInfo = $psi

$stdoutLines = New-Object System.Collections.Generic.List[string]
$stderrLines = New-Object System.Collections.Generic.List[string]

$stdoutHandler = {
    param($sender, $e)
    if ($null -ne $e.Data) {
        Write-Host $e.Data
        $Event.MessageData.Add($e.Data)
    }
}
$stderrHandler = {
    param($sender, $e)
    if ($null -ne $e.Data) {
        Write-Host $e.Data -ForegroundColor Yellow
        $Event.MessageData.Add($e.Data)
    }
}

Register-ObjectEvent -InputObject $process -EventName OutputDataReceived `
    -Action $stdoutHandler -MessageData $stdoutLines | Out-Null
Register-ObjectEvent -InputObject $process -EventName ErrorDataReceived  `
    -Action $stderrHandler -MessageData $stderrLines | Out-Null

Write-Header "LANCEMENT"
Write-Stat "Executable" $fullPath "Green"
Write-Stat "Arguments"  $(if ($Arguments) { $Arguments } else { "(aucun)" }) "Green"
Write-Stat "Demarre a"  (Get-Date -Format "yyyy-MM-dd HH:mm:ss") "Green"
Write-Host ""

$wallClock = New-Object System.Diagnostics.Stopwatch
$wallClock.Start()

[void]$process.Start()
$process.BeginOutputReadLine()
$process.BeginErrorReadLine()

$peakWorkingSet  = 0L
$peakPagedMem    = 0L
$peakVirtualMem  = 0L
$peakThreads     = 0

# Polling pendant l'execution : toutes les valeurs de pic doivent etre
# lues ICI car PeakPagedMemorySize64 et PeakVirtualMemorySize64
# deviennent 0 une fois le processus termine.
while (-not $process.HasExited) {
    try {
        $process.Refresh()
        if ($process.PeakWorkingSet64       -gt $peakWorkingSet) { $peakWorkingSet = $process.PeakWorkingSet64 }
        if ($process.PeakPagedMemorySize64  -gt $peakPagedMem)   { $peakPagedMem   = $process.PeakPagedMemorySize64 }
        if ($process.PeakVirtualMemorySize64 -gt $peakVirtualMem) { $peakVirtualMem = $process.PeakVirtualMemorySize64 }
        if ($process.Threads.Count          -gt $peakThreads)    { $peakThreads    = $process.Threads.Count }
    } catch { }
    Start-Sleep -Milliseconds 100
}

$process.WaitForExit()
$wallClock.Stop()

# Derniere lecture pour PeakWorkingSet64 (la seule qui survit apres la fin)
try {
    $process.Refresh()
    if ($process.PeakWorkingSet64 -gt $peakWorkingSet) { $peakWorkingSet = $process.PeakWorkingSet64 }
    if ($process.Threads.Count    -gt $peakThreads)    { $peakThreads    = $process.Threads.Count }
} catch { }

$wallTime   = $wallClock.Elapsed
$exitCode   = $process.ExitCode
$kernelTime = $process.PrivilegedProcessorTime
$userTime   = $process.UserProcessorTime
$cpuTotal   = New-Object TimeSpan ($kernelTime.Ticks + $userTime.Ticks)

Get-EventSubscriber | Where-Object { $_.SourceObject -eq $process } | Unregister-Event -Force

Write-Header "STATISTIQUES D'EXECUTION"

Write-Stat "Duree totale (wall-clock)"  (Format-TimeSpan $wallTime)   "Cyan"
Write-Stat "Temps CPU total"            (Format-TimeSpan $cpuTotal)   "Cyan"
Write-Stat "  > Temps user"             (Format-TimeSpan $userTime)   "Cyan"
Write-Stat "  > Temps noyau (kernel)"   (Format-TimeSpan $kernelTime) "Cyan"

if ($wallTime.TotalSeconds -gt 0) {
    $cpuEfficiency = ($cpuTotal.TotalSeconds / $wallTime.TotalSeconds) * 100
    Write-Stat "Utilisation CPU effective" ("{0:N1} %" -f $cpuEfficiency) "Cyan"
}

Write-Host ""
Write-Stat "Memoire max (Working Set)"  (Format-Bytes $peakWorkingSet) "Magenta"
Write-Stat "Memoire paginee max"        (Format-Bytes $peakPagedMem)   "Magenta"
Write-Stat "Memoire virtuelle max"      (Format-Bytes $peakVirtualMem) "Magenta"

Write-Host ""
Write-Stat "Pic de threads" "$peakThreads" "Yellow"

Write-Host ""
$exitColor = if ($exitCode -eq 0) { "Green" } else { "Red" }
Write-Stat "Code de retour (exit code)" "$exitCode" $exitColor

$hasStderr = $stderrLines.Count -gt 0
$stderrMsg = if ($hasStderr) { "OUI (affichee en jaune)" } else { "Aucune" }
$stderrCol = if ($hasStderr) { "Red" } else { "Green" }
Write-Stat "Sortie d'erreur" $stderrMsg $stderrCol

Write-Host ""
Write-Host ("-" * 60) -ForegroundColor DarkGray
Write-Host ""

exit $exitCode

