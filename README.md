Set up:
1. Install the pact cli
2. Get a pactflow account
3. Set environment variables: ND_PACT_BROKER_URL and ND_PACT_BROKER_TOKEN

Steps:
1. make compile
2. make pact-provider - Runs the pact provider tests to confirm consumer pacts have been verified.
3. make pact-release - Runs the pact provider tests to confirm consumer pacts have been verified and tags with the environment being released too
