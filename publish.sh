#!/bin/bash

SUCCESS=true
if [ "${1}" != "true" ]; then
  SUCCESS=false
fi

PACTICIPANT=$(basename `git rev-parse --show-toplevel`) # Always use the repo name as the provider name
OAS=$(cat build/openapi.json | base64) # Remember to run ./gradlew generateOpenApiDocs
REPORT=$(echo "OAS generated by code" | base64)
COMMIT=$(git rev-parse HEAD)
BRANCH=$(git rev-parse --abbrev-ref HEAD)

echo "==> Uploading OAS to Pactflow"
curl \
  -X PUT \
  -H "Authorization: Bearer ${PACT_BROKER_TOKEN}" \
  -H "Content-Type: application/json" \
  "${PACT_BROKER_BASE_URL}/contracts/provider/${PACTICIPANT}/version/${COMMIT}" \
  -d '{
   "content": "'$OAS'",
   "contractType": "oas",
   "contentType": "application/json",
   "verificationResults": {
     "success": '$SUCCESS',
     "content": "'$REPORT'",
     "contentType": "text/plain",
     "verifier": "verifier"
   }
 }'

echo ""
echo ""
echo "==> Setting branch on Pactflow"
curl \
  -X PUT \
  -H "Authorization: Bearer ${PACT_BROKER_TOKEN}" \
  -H "Content-Type: application/json" \
  "${PACT_BROKER_BASE_URL}/pacticipants/${PACTICIPANT}/branches/${BRANCH}/versions/${COMMIT}" \
  -d '{}'

echo ""
echo ""
echo "==> Setting tags on Pactflow"
curl \
  -X PUT \
  -H "Authorization: Bearer ${PACT_BROKER_TOKEN}" \
  -H "Content-Type: application/json" \
  "${PACT_BROKER_BASE_URL}/pacticipants/${PACTICIPANT}/versions/${COMMIT}/tags/${BRANCH}" \
  -d '{}'
