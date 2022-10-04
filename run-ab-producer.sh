#! /bin/bash -e

# ab -n 100 -c 1 -p <(echo '""') http://localhost:8081/producer/1

ab -n "${1:-100}" -c 10 -p <(echo '""') http://localhost:8081/producer