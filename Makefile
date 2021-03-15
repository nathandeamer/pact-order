SHELL:=/bin/bash

GITHUB_COMMIT_HASH?=$(shell git rev-parse --verify HEAD)

ENV?=dev

compile:
	./gradlew clean build

pact-provider:
	./gradlew -Dpact.verifier.publishResults=true -Dpact.provider.version=${GITHUB_COMMIT_HASH} test

pact-release:
	./gradlew -Dpact.verifier.publishResults=true -Dpact.provider.version=${GITHUB_COMMIT_HASH} -Dpact.provider.tag=${ENV} test