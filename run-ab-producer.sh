#! /bin/bash -e


ab -n 100 -c 10 -p <(echo '""') http://localhost:8081/producer