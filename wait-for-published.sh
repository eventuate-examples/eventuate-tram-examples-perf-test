#! /bin/bash -e

while [ "$(echo 'select count(*) from message where published = 0\G' |  ./mysql-cli.sh -i   | sed -e '/:/!d' -e 's/.*: //')" -ne "0" ] ; do
  sleep 5
done
