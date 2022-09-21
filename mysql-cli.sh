#! /bin/bash -e

docker run ${1:--it} \
   --network=${PWD##*/}_default --rm \
   mysql/mysql-server:8.0.27-1.2.6-server \
   sh -c 'exec mysql -hmysql  -uroot -prootpassword -o eventuate'
