#!/bin/sh

cd target/site
if [ -s 1.json ];
then
jq -n '[inputs[]] | group_by(.id) | map({line:.[0].line, elements:map(.elements | .[]), name:.[0].name, description:.[0].description, id:.[0].id, keyword:.[0].keyword,uri:.[0].uri, tags:.[0].tags})' *.json > consolidated.json;
  if [ $? -ne 0 ]
  then
      echo "Consolidated report cannot be created because jq is not installed. Please install jq or switch to **/*.json to build reports";
  fi
fi