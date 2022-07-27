import org.bouncycastle.util.encoders.Hex;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Hash;
import org.web3j.rlp.RlpEncoder;
import org.web3j.rlp.RlpList;
import org.web3j.rlp.RlpString;
import org.web3j.utils.Numeric;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Random;

public class Main {

    public static void main(String[] args) throws Exception{
        int num = 0;
        long nonce = 0;
        boolean i = false;
        byte[] randomBytes;
        byte[] addressAsBytes;
        byte[] calculatedAddressAsBytes;

        String prefStart = "0x"; //change this to change contract start

        long startTime = System.nanoTime(); //starts timer

        while(!i) {
            randomBytes = new byte[32];
            new Random().nextBytes(randomBytes); //generates random bytes for private key
            String privateKey = new String(Hex.encode(randomBytes)); //encodes bytes into hex

            String address = "0x" + getPublicKeyInHex(privateKey); //sends private key to public key method
            addressAsBytes = Numeric.hexStringToByteArray(address); //converts hex address to bytes

            //RLP encodes address (as bytes) and nonce, and does a keccak256 hash on it
            calculatedAddressAsBytes =
                    Hash.sha3(RlpEncoder.encode(
                            new RlpList(
                                    RlpString.create(addressAsBytes),
                                    RlpString.create((nonce)))));
            calculatedAddressAsBytes = Arrays.copyOfRange(calculatedAddressAsBytes,
                    12, calculatedAddressAsBytes.length);
            String calculatedAddressAsHex = Numeric.toHexString(calculatedAddressAsBytes);

            //if the contract address does not start with specified, while loop continues
            if(!calculatedAddressAsHex.startsWith(prefStart)) {
                i = false;
            } else {
                i = true;
                long endTime = System.nanoTime(); //stops timer
                long timeElapsed = endTime - startTime; //timer math
                System.out.println(timeElapsed / 1000000000 + " second run time");
                System.out.println("Found contract starting with " + prefStart);
                System.out.println("Nonce: " + nonce);
                System.out.println("Address: " + address);
                System.out.println("Private Key: 0x" + privateKey);
                System.out.println("Contract Address: " + calculatedAddressAsHex);
                System.out.println("Scanned " + num + " contract addresses to find this");
            }
            num++;
        }
    }

    //method takes in private key, and returns public key
    public static String getPublicKeyInHex(String privateKeyInHex) {
        BigInteger privateKeyInBT = new BigInteger(privateKeyInHex, 16);
        ECKeyPair aPair = ECKeyPair.create(privateKeyInBT);
        BigInteger publicKeyInBT = aPair.getPublicKey();
        String sPublicKeyInHex = Hash.sha3(publicKeyInBT.toString(16));
        return sPublicKeyInHex.substring(26);
    }
}
