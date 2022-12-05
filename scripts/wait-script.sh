#!/usr/bin/env bash

IFS="," read -ra PORTS <<<"$WAIT_PORTS"

echo "Sleeping for 30 seconds"
sleep 30
echo "Wake up Neo. Sorry copied)"

PIDs=()
for port in "${PORTS[@]}"; do
  "$(pwd)"/scripts/wait-for.sh -t 120 "localhost:$port" -- echo "Host localhost:$port is active" &
  PIDs+=($!)
done

for pid in "${PIDs[@]}"; do
  if ! wait "${pid}"; then
    exit 1
  fi
done
