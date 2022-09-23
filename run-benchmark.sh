#! /bin/bash -e


# Populate the database

./gradlew consumerComposeDown producerComposeUp

ab -n 1000 -c 10 -p <(echo '""') http://localhost:8081/producer

./wait-for-published.sh

./gradlew consumerComposeUp -P consumerConcurrency=${1?}

open "http://localhost:9090/graph?g0.expr=rate(eventuate_cdc_polling_published_total%5B30s%5D)&g0.tab=0&g0.stacked=0&g0.range_input=5m&g1.expr=rate(consumer_events_consumed_total%5B30s%5D)&g1.tab=0&g1.stacked=0&g1.range_input=15m"
