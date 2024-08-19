#! /bin/bash

BASEDIR=$(dirname $0)
HELP_TEXT="
This is a small script to help with deployment.
This script will create a namespace (if it does not already exist), 
create and install a sealed secret from a local secret file if you provide the parameter
and deploy the helm chart.

The script has some parameters:

-n, --namespace - the namespace where the management console should be deployed to
-g, --github-auth-secret - (optional) the path to the secret that is used to retrieve the data
-f, --value-file - the path to the value file for the helm deployment
-h, --help - prints this message
-u, --upgrade - upgrade the helm installation instead of installing it
"
TARGET=all
KEY=
VALUE=
while [[ "$#" -gt 0 ]]; do
  case "${1}" in
    (-t | --target)
      TARGET=${2}
      shift 2
    ;;
    (-k | --key)
      KEY=${2}
      shift 2
    ;;
    (-v | --value)
      VALUE=${2}
      shift 2
    ;;
    (-h | --help)
      echo "${HELP_TEXT}"
      exit 0
    ;;
    (*)
        echo "Unknown parameter '${1}'"
        exit 1    # error
    ;;
  esac
done


if [ -z "$KEY" ]; then
  echo "Error: please specify a key"
  exit 1
fi

if [ -z "$VALUE" ]; then
  echo "Error: please specify a value"
  exit 1
fi

function add_secret() {
  SECRET_FILE="$BASEDIR/secrets/edc-dev-$1-management-application-secret.yaml"
  SEALED_SECRET_FILE="$BASEDIR/sealed-secrets/edc-dev-$1-management-application-secret.yaml"
  NAMESPACE="edc-dev-$1-management"
  KEY=$2
  VALUE=$3
  TMP_FILE=$(mktemp)
  kubectl patch --dry-run=client -p '{"data":{"'"$KEY"'": "'"$(echo "$VALUE" | base64)"'"}}' -f $SECRET_FILE -o yaml > $TMP_FILE
  cp $TMP_FILE $SECRET_FILE
  rm $TMP_FILE
  kubeseal -f "$SECRET_FILE" -w "$SEALED_SECRET_FILE" -n "$NAMESPACE"
}

if [ "$TARGET" = "provider" ] || [ "$TARGET" = "all" ]; then
  add_secret "provider" "$KEY" "$VALUE"
fi

if [ "$TARGET" = "consumer" ] || [ "$TARGET" = "all" ]; then
  add_secret "consumer" "$KEY" "$VALUE"
fi