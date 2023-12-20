#!/bin/bash
source ~/.bash_profile
docker kill $(docker ps -q)
docker images purge
echo "Finisihed"