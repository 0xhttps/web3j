# Contract Address Scanner

This Java program generates random private keys, calculates their corresponding Ethereum addresses, and checks if the derived contract address matches a specified prefix.

## Usage

1. **Set Contract Start Prefix:**
   - Modify the `prefStart` variable in the code to the desired contract address prefix.

2. **Compile and Run:**
   - Compile and run the `Main` class to start the contract address scanning process.

3. **Output:**
   - The program will output details when a matching contract address is found.
     - Run time
     - Contract starting with the specified prefix
     - Nonce
     - Address
     - Private Key
     - Calculated Contract Address
     - Number of scanned contract addresses to find the match

## Note

- Ensure you have the necessary dependencies (e.g., Bouncy Castle library, Web3j) included in your project.

- The `getPublicKeyInHex` method converts a private key to a corresponding public key.

- Adjust the loop conditions or other parameters based on your specific requirements.
