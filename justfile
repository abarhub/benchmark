set shell := ["powershell.exe", "-c"]

default:
  just --list



bench *ARGS:
  cd .\java ; just bench {{ARGS}}
  cd .\rust ; just benchopt {{ARGS}}
  cd .\python ; just bench {{ARGS}}
  cd .\javascript ; just bench {{ARGS}}
  cd .\go ; just bench {{ARGS}}
  

benchall *ARGS:
  cd .\java ; just bench multi {{ARGS}}
  cd .\java ; just bench multithread {{ARGS}}
  cd .\rust ; just benchopt {{ARGS}}
  cd .\python ; just bench {{ARGS}}
  cd .\javascript ; just bench {{ARGS}}
  cd .\go ; just bench {{ARGS}}


benchallmem *ARGS:
  cd .\java ; just benchmem multi {{ARGS}}
  cd .\java ; just benchmem multithread {{ARGS}}
  cd .\rust ; just benchmem {{ARGS}}
  cd .\python ; just benchmem {{ARGS}}
  cd .\javascript ; just benchmem {{ARGS}}
  cd .\go ; just benchmem {{ARGS}}
