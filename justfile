set shell := ["powershell.exe", "-c"]

default:
  just --list



bench *ARGS:
  cd .\java ; just bench {{ARGS}}
  cd .\rust ; just benchopt {{ARGS}}
  



