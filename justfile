set shell := ["powershell.exe", "-c"]

default:
  just --list



bench *ARGS:
  cd .\java ; just bench {{ARGS}}
  cd .\rust ; just benchopt {{ARGS}}
  

benchall *ARGS:
  cd .\java ; just bench multi {{ARGS}}
  cd .\java ; just bench multithread {{ARGS}}
  cd .\rust ; just benchopt {{ARGS}}

