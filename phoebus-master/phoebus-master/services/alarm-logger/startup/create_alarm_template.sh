#!/bin/sh
if [ $# -ne 1 ]
then
    echo "Usage: create_alarm_index.sh accelerator"
    exit 1
fi

es_host=localhost
es_port=9200

# Create the elastic template with the correct mapping for alarm state messages.
curl -XPUT http://${es_host}:${es_port}/_template/${1}_alarms_state_template -H 'Content-Type: application/json' -d'
{
  "index_patterns":["*_alarms_state*"],
  "mappings" : {  
    "alarm" : {
        "properties" : {
          "APPLICATION-ID" : {
            "type" : "text"
          },
          "config" : {
            "type" : "keyword"
          },
          "pv" : {
            "type" : "keyword"
          },
          "severity" : {
            "type" : "keyword"
          },
          "message" : {
            "type" : "text"
          },
          "value" : {
            "type" : "text"
          },
          "time" : {
            "type" : "date",
            "format" : "yyyy-MM-dd HH:mm:ss.SSS"
          },
          "message_time" : {
            "type" : "date",
            "format" : "yyyy-MM-dd HH:mm:ss.SSS"
          },
          "current_severity" : {
            "type" : "keyword"
          },
          "current_message" : {
            "type" : "text"
          },
          "mode" : {
            "type" : "text"
          }
        }
      }
  }
}
'

# Create the elastic template with the correct mapping for alarm state messages.
curl -XPUT http://${es_host}:${es_port}/_template/${1}_alarms_cmd_template -H 'Content-Type: application/json' -d'
{
  "index_patterns":["*_alarms_cmd*"],
  "mappings" : {  
    "alarm_cmd" : {
        "properties" : {
          "APPLICATION-ID" : {
            "type" : "text"
          },
          "config" : {
            "type" : "keyword"
          },
          "user" : {
            "type" : "keyword"
          },
          "host" : {
            "type" : "keyword"
          },
          "command" : {
            "type" : "keyword"
          },
          "message_time" : {
            "type" : "date",
            "format" : "yyyy-MM-dd HH:mm:ss.SSS"
          }
        }
      }
  }
}
'
