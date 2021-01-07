/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package demo;

import org.tron.tronj.abi.FunctionEncoder;
import org.tron.tronj.abi.FunctionReturnDecoder;
import org.tron.tronj.abi.TypeReference;
import org.tron.tronj.abi.datatypes.generated.Bytes10;
import org.tron.tronj.abi.datatypes.generated.Uint256;
import org.tron.tronj.abi.datatypes.generated.Uint32;
import org.tron.tronj.abi.datatypes.*;
import org.tron.tronj.client.contract.Contract;
import org.tron.tronj.client.transaction.TransactionBuilder;
import org.tron.tronj.client.TronClient;
import org.tron.tronj.proto.Chain.Transaction;
import org.tron.tronj.proto.Contract.TriggerSmartContract;
import org.tron.tronj.proto.Response.BlockExtention;
import org.tron.tronj.proto.Response.BlockListExtention;
import org.tron.tronj.proto.Response.TransactionExtention;
import org.tron.tronj.proto.Response.TransactionReturn;
import org.tron.tronj.abi.datatypes.Function;
import org.tron.tronj.abi.datatypes.StaticArray;
import org.tron.tronj.abi.datatypes.Type;
import org.tron.tronj.abi.datatypes.generated.Uint256;
import org.tron.tronj.api.GrpcAPI.BytesMessage;
import java.math.BigInteger;
import java.util.*;
import org.tron.tronj.proto.Chain.Block;
import org.tron.tronj.proto.Chain.BlockHeader;

import org.tron.tronj.proto.Response.DelegatedResourceAccountIndex;
import org.tron.tronj.utils.Base58Check;
import com.google.protobuf.ByteString;
//demo classes
import demo.smartContract.trc20.Trc20Demo;
import demo.smartContract.SmartContractDemo;

public class App {
    public String encodeFunctionCalling() {
        System.out.println("! function sam(bytes _, bool _, address _, uint[])");
        Function function = new Function("sam",
                Arrays.asList(new DynamicBytes("dave".getBytes()), new Bool(true),
                        new Address("T9yKC9LCoVvmhaFxKcdK9iL18TUWtyFtjh"),
                        new DynamicArray<>(
                                new Uint(BigInteger.ONE), new Uint(BigInteger.valueOf(2)), new Uint(BigInteger.valueOf(3)))),
                Collections.emptyList());
        String encodedHex = FunctionEncoder.encode(function);
        return encodedHex;
    }

    public void decodeFunctionReturn() {
        Function function = new Function("test", Collections.<Type>emptyList(),
                Arrays.asList(new TypeReference<Uint>() {
                }, new TypeReference<Address>() {
                }));

        List<Type> outputs =
                FunctionReturnDecoder.decode("0000000000000000000000000000000000000000000000000000000000000037"
                                + "00000000000000000000000028263f17875e4f277a72f6c6910bb7a692108b3e",
                        function.getOutputParameters());
        for (Type obj : outputs) {
            System.out.println(obj.getTypeAsString() + "  " + obj.toString());
            if (Uint.class.isInstance(obj)) {
                System.out.println("  parsed value => " + ((Uint) obj).getValue());
            }
        }
        // assertEquals(outputs,
        //    (Arrays.asList(new Uint(BigInteger.valueOf(55)), new Uint(BigInteger.valueOf(7)))));
    }

    /*public void signTransaction() {
        System.out.println("============= signTransaction =============");
        TronClient client = TronClient.ofShasta("3333333333333333333333333333333333333333333333333333333333333333");
        try {
            client.signTransaction(, 1_000_000L, 3L,1);
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
    }*/

    public void trc20Encode() {
        Function trc20Transfer = new Function("transfer",
                Arrays.asList(new Address("TV3KSjZHF4o6bC92SMrjhNJ3RE65xHNDuo"),
                        new Uint256(BigInteger.valueOf(1000).multiply(BigInteger.valueOf(10).pow(18)))),
                Arrays.asList(new TypeReference<Bool>() {
                }));

        String encodedHex = FunctionEncoder.encode(trc20Transfer);
        System.out.println("! encoding a TRC20 transfer");
        System.out.println(encodedHex);
    }


    /*public void sendTrx() {
        System.out.println("============= TRC transfer =============");
        TronClient client = TronClient.ofNile("9fd7fb6311aa3251fca212ddf18a3e46a9956230939052e4df59b56f9de45dd5");
        try {
            TransactionExtention transactionExtention = client.transfer("TLtrDb1udekjDumnrf3EVeke3Q6pHkZxjm", "TP8LKAf3R3FHDAcrQXuwBEWmaGrrUdRvzb", 1_000_000);
            Transaction signedTxn = client.signTransaction(transactionExtention);
            System.out.println(signedTxn.toString());
            //broadcast transactionx
            TransactionReturn ret = client.broadcastTransaction(signedTxn);
            System.out.println("======== Result ========\n" + ret.toString());
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
    }*/

    public void sendTrx() {
        System.out.println("============= TRC transfer =============");
        TronClient client = TronClient.ofNile("9fd7fb6311aa3251fca212ddf18a3e46a9956230939052e4df59b56f9de45dd5");
        try {
            TransactionExtention transaction = client.transfer("TLtrDb1udekjDumnrf3EVeke3Q6pHkZxjm", "TP8LKAf3R3FHDAcrQXuwBEWmaGrrUdRvzb", 1_000_000);
            Transaction signedTxn = client.signTransaction(transaction);
            System.out.println(signedTxn.toString());
            TransactionReturn ret = client.broadcastTransaction(signedTxn);
            System.out.println("======== Result ========\n" + ret.toString());
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
    }
    /*public void transferTrc10(){
        System.out.println("============= transferTrc10 =============");
        TronClient client = TronClient.ofNile("9fd7fb6311aa3251fca212ddf18a3e46a9956230939052e4df59b56f9de45dd5");
        try {
            TransactionExtention transactionExtention = client.transferTrc10("TLtrDb1udekjDumnrf3EVeke3Q6pHkZxjm", "TP8LKAf3R3FHDAcrQXuwBEWmaGrrUdRvzb",
                    1000016, 1_000_000);
            Transaction signedTxn = client.signTransaction(transactionExtention);
            System.out.println(signedTxn.toString());
            //broadcast transactionx
            TransactionReturn ret = client.broadcastTransaction(signedTxn);
            System.out.println("======== Result ========\n" + ret.toString());
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
    }*/

    public void transferTrc10(){
        System.out.println("============= transferTrc10 =============");
        TronClient client = TronClient.ofNile("9fd7fb6311aa3251fca212ddf18a3e46a9956230939052e4df59b56f9de45dd5");
        try {
            TransactionExtention transactionExtention = client.transferTrc10("TLtrDb1udekjDumnrf3EVeke3Q6pHkZxjm", "TP8LKAf3R3FHDAcrQXuwBEWmaGrrUdRvzb",
                    1000016, 1_000_000);
            Transaction signedTxn = client.signTransaction(transactionExtention);
            System.out.println(signedTxn.toString());
            TransactionReturn ret = client.broadcastTransaction(signedTxn);
            System.out.println("======== Result ========\n" + ret.toString());
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
    }

    /*public void freezeBalance() {
        System.out.println("============= freeze balance =============");
        TronClient client = TronClient.ofNile("9fd7fb6311aa3251fca212ddf18a3e46a9956230939052e4df59b56f9de45dd5");
        try {
            TransactionExtention transaction = client.freezeBalance("TLtrDb1udekjDumnrf3EVeke3Q6pHkZxjm", 1_000_000L, 3L,1);
            Transaction signedTxn = client.signTransaction(transaction);
            System.out.println(signedTxn.toString());
            //broadcast transactionx
            TransactionReturn ret = client.broadcastTransaction(signedTxn);
            System.out.println("======== Result ========\n" + ret.toString());
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
    }*/

    public void freezeBalance() {
        System.out.println("============= freeze balance =============");
        TronClient client = TronClient.ofNile("9fd7fb6311aa3251fca212ddf18a3e46a9956230939052e4df59b56f9de45dd5");
        try {
            TransactionExtention transaction = client.freezeBalance("TLtrDb1udekjDumnrf3EVeke3Q6pHkZxjm", 1_000_000L, 3L,1,"TMmbeRPnFhXC7BPLaF2M1HCsoE4jwZNB7b");
            Transaction signedTxn = client.signTransaction(transaction);
            System.out.println(signedTxn.toString());
            TransactionReturn ret = client.broadcastTransaction(signedTxn);
            System.out.println("======== Result ========\n" + ret.toString());
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
    }

    public void unFreezeBalance() {
        System.out.println("============= unFreeze balance =============");
        TronClient client = TronClient.ofNile("9fd7fb6311aa3251fca212ddf18a3e46a9956230939052e4df59b56f9de45dd5");
        try {
            TransactionExtention transaction = client.unfreezeBalance("TLtrDb1udekjDumnrf3EVeke3Q6pHkZxjm", 1, "TMmbeRPnFhXC7BPLaF2M1HCsoE4jwZNB7b");
            Transaction signedTxn = client.signTransaction(transaction);
            System.out.println(signedTxn.toString());
            TransactionReturn ret = client.broadcastTransaction(signedTxn);
            System.out.println("======== Result ========\n" + ret.toString());
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
    }

    public void getBlockByNum() {
        System.out.println("============= getBlockByNum =============");
        TronClient client = TronClient.ofNile("3333333333333333333333333333333333333333333333333333333333333333");
        try {
            Block block = client.getBlockByNum(10);
            System.out.println(block);
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
    }

    /*public void getBlockByLatestNumSolidity() {
        System.out.println("============= getBlockByNum =============");
        TronClient client = TronClient.ofNile("3333333333333333333333333333333333333333333333333333333333333333");
        try {
            BlockListExtention block = client.getBlockByLatestNumSolidity(1);
            System.out.println(block);
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
    }*/

    public void getNowBlock() {
        System.out.println("============= getNowBlock =============");
        TronClient client = TronClient.ofShasta("3333333333333333333333333333333333333333333333333333333333333333");
        try {
            System.out.println(client.getNowBlock());
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
    }

    public void getNowBlockSolidity() {
        System.out.println("============= getNowBlockSolidity =============");
        TronClient client = TronClient.ofNile("3333333333333333333333333333333333333333333333333333333333333333");
        try {
            System.out.println(client.getNowBlockSolidity());
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
    }

    public void getNodeInfo() {
        System.out.println("============= getNodeInfo=============");
        TronClient client = TronClient.ofNile("3333333333333333333333333333333333333333333333333333333333333333");
        try {
            System.out.println(client.getNodeInfo());
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
    }

    public void listNodes() {
        System.out.println("============= listNodes=============");
        TronClient client = TronClient.ofNile("3333333333333333333333333333333333333333333333333333333333333333");
        try {
            System.out.println(client.listNodes());
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
    }

    public void getTransactionInfoByBlockNum() {
        System.out.println("============= getTransactionInfoByBlockNum =============");
        TronClient client = TronClient.ofNile("3333333333333333333333333333333333333333333333333333333333333333");
        try {
            System.out.println(client.getTransactionInfoByBlockNum(-100));
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
    }

    // public void sendTrc20Transaction() {
    //     System.out.println("============ TRC20 transfer =============");
    //     // Any of `ofShasta`, `ofMainnet`.
    //     TronClient client = TronClient.ofNile("3333333333333333333333333333333333333333333333333333333333333333");

    //     // transfer(address _to,uint256 _amount) returns (bool)
    //     // _to = TVjsyZ7fYF3qLF6BQgPmTEZy1xrNNyVAAA
    //     // _amount = 10 * 10^18
    //     Function trc20Transfer = new Function("transfer",
    //         Arrays.asList(new Address("TVjsyZ7fYF3qLF6BQgPmTEZy1xrNNyVAAA"),
    //             new Uint256(BigInteger.valueOf(10).multiply(BigInteger.valueOf(10).pow(18)))),
    //         Arrays.asList(new TypeReference<Bool>() {}));

    //     String encodedHex = FunctionEncoder.encode(trc20Transfer);
    //     TriggerSmartContract trigger =
    //         TriggerSmartContract.newBuilder()
    //             .setOwnerAddress(TronClient.parseAddress("TJRabPrwbZy45sbavfcjinPJC18kjpRTv8"))
    //             .setContractAddress(TronClient.parseAddress("TF17BgPaZYbz8oxbjhriubPDsA7ArKoLX3")) // JST
    //             .setData(TronClient.parseHex(encodedHex))
    //             .build();

    //     System.out.println("trigger:\n" + trigger);

    //     TransactionExtention txnExt = client.blockingStub.triggerContract(trigger);
    //     System.out.println("txn id => " + TronClient.toHex(txnExt.getTxid().toByteArray()));

    //     Transaction unsignedTxn = txnExt.getTransaction.toBuilder()
    //         .setRawData(txnExt.getTransaction().getRawData().toBuilder().setFeeLimit(10000000L))
    //         .build();

    //     Transaction signedTxn = client.signTransaction(unsignedTxn);

    //     System.out.println(signedTxn.toString());
    //     TransactionReturn ret = client.blockingStub.broadcastTransaction(signedTxn);
    //     System.out.println("======== Result ========\n" + ret.toString());
    // }

    /*public void transferTrc20() {
        // Any of `ofShasta`, `ofMainnet`.
        TronClient client = TronClient.ofNile("3333333333333333333333333333333333333333333333333333333333333333");
        try {
            //JST transfer
            client.transferTrc20("TJRabPrwbZy45sbavfcjinPJC18kjpRTv8", "TVjsyZ7fYF3qLF6BQgPmTEZy1xrNNyVAAA", "TF17BgPaZYbz8oxbjhriubPDsA7ArKoLX3", 1000000000L, 10L, 18);
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
    }*/

    public void getTransactionInfoById() {
        System.out.println("============= getTransactionInfoById =============");
        TronClient client = TronClient.ofNile("3333333333333333333333333333333333333333333333333333333333333333");
        try {
            System.out.println(client.getTransactionInfoById("786c7516df88941e33ea44f03e637bd8c1ddcfd058634574102c6e3cfb93de0d"));
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
    }

    public void getTransactionById() {
        System.out.println("============= getTransactionById =============");
        TronClient client = TronClient.ofNile("3333333333333333333333333333333333333333333333333333333333333333");
        try {
            System.out.println(client.getTransactionById("786c7516df88941e33ea44f03e637bd8c1ddcfd058634574102c6e3cfb93de0d"));
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
    }

    public void getTransactionByIdSolidity() {
        System.out.println("============= getTransactionByIdSolidity =============");
        TronClient client = TronClient.ofShasta("3333333333333333333333333333333333333333333333333333333333333333");
        try {
            System.out.println(client.getTransactionByIdSolidity("3535304212e0090d421ec88cd194d35875b748c0ad453fcde6d7b4d43e852ced"));
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
    }

    /**
     * This is a constant call demo
     */
    public void viewContractName() {
        TronClient client = TronClient.ofNile("3333333333333333333333333333333333333333333333333333333333333333");
        try {
            Function viewName = new Function("name", Collections.emptyList(), Collections.emptyList());
            TransactionExtention txnExt = client.constantCall("TJRabPrwbZy45sbavfcjinPJC18kjpRTv8", "TF17BgPaZYbz8oxbjhriubPDsA7ArKoLX3", viewName);
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
    }


    public void getAccount(){
        System.out.println("============= getAccount =============");
        TronClient client = TronClient.ofShasta("3333333333333333333333333333333333333333333333333333333333333333");
        try {
            System.out.println(client.getAccount("TCta4rhDcnHvEQTX8NrM1WTowR41msiAwj").getAccountName());
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
    }

    public void updateAccount(){
        System.out.println("============= updateAccount =============");
        TronClient client = TronClient.ofNile("2ed817d9e8ee88a04b4ec4f348382f7149a09a305d03c9170233f5d180f46e6a");
        try {
            TransactionExtention transaction = client.updateAccount("TMmbeRPnFhXC7BPLaF2M1HCsoE4jwZNB7b","");
            System.out.println(transaction);
            Transaction signedTxn = client.signTransaction(transaction);
            System.out.println(signedTxn.toString());
            TransactionReturn ret = client.broadcastTransaction(signedTxn);
            System.out.println("======== Result ========\n" + ret.toString());
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
    }

    public void createAccount(){
        System.out.println("============= createAccount =============");
        TronClient client = TronClient.ofNile("2ed817d9e8ee88a04b4ec4f348382f7149a09a305d03c9170233f5d180f46e6a");
        try {
            TransactionExtention transaction = client.createAccount("TMmbeRPnFhXC7BPLaF2M1HCsoE4jwZNB7b","TH9ADEXR4wjHtnrdv7Ea1RchmCn5b5GQ6v");
            System.out.println(transaction);
            Transaction signedTxn = client.signTransaction(transaction);
            System.out.println(signedTxn.toString());
            TransactionReturn ret = client.broadcastTransaction(signedTxn);
            System.out.println("======== Result ========\n" + ret.toString());
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
    }

    public void getAccountResource(){
        System.out.println("============= getAccountResource =============");
        TronClient client = TronClient.ofShasta("3333333333333333333333333333333333333333333333333333333333333333");
        try {
            System.out.println(client.getAccountResource("TKwVM5tsELuTE3a5SUCWiQyVtEgxejL5Wj"));
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
    }

    public void getAccountNet(){
        System.out.println("============= getAccountNet =============");
        TronClient client = TronClient.ofShasta("3333333333333333333333333333333333333333333333333333333333333333");
        try {
            System.out.println(client.getAccountNet("TKwVM5tsELuTE3a5SUCWiQyVtEgxejL5Wj"));
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
    }

    public void getDelegatedResource(){
        System.out.println("============= getDelegatedResource =============");
        TronClient client = TronClient.ofNile("3333333333333333333333333333333333333333333333333333333333333333");
        try {

            System.out.println(client.getDelegatedResource("TLtrDb1udekjDumnrf3EVeke3Q6pHkZxjm","TMmbeRPnFhXC7BPLaF2M1HCsoE4jwZNB7b"));
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
    }

    public void getDelegatedResourceAccountIndex(){
        System.out.println("============= getDelegatedResourceAccountIndex =============");
        TronClient client = TronClient.ofNile("3333333333333333333333333333333333333333333333333333333333333333");
        try {
            DelegatedResourceAccountIndex accountIndex = client.getDelegatedResourceAccountIndex("TLtrDb1udekjDumnrf3EVeke3Q6pHkZxjm");
            ByteString account = accountIndex.getAccount();

            System.out.println("Accounts: "+Base58Check.bytesToBase58(client.parseAddress(client.toHex(account)).toByteArray()));

            int fromAccountsCount = accountIndex.getFromAccountsCount();
            for(int i =0;i<fromAccountsCount;i++){
                ByteString fromAccounts = accountIndex.getFromAccounts(i);
                System.out.println("fromAccounts: "+Base58Check.bytesToBase58(client.parseAddress(client.toHex(fromAccounts)).toByteArray()));
            }

            int toAccountsCount = accountIndex.getToAccountsCount();
            for(int i =0;i<toAccountsCount;i++){
                ByteString toAccounts = accountIndex.getToAccounts(i);
                System.out.println("toAccounts: "+Base58Check.bytesToBase58(client.parseAddress(client.toHex(toAccounts)).toByteArray()));
            }

        } catch (Exception e) {
            System.out.println("error: " + e);
        }
    }

    public void getAccountSolidity(){
        System.out.println("============= getAccountSolidity =============");
        TronClient client = TronClient.ofShasta("3333333333333333333333333333333333333333333333333333333333333333");
        try {
            System.out.println(client.getAccountSolidity("TKwVM5tsELuTE3a5SUCWiQyVtEgxejL5Wj"));
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
    }

    public void getRewardSolidity(){
        System.out.println("============= getRewardSolidity =============");
        TronClient client = TronClient.ofShasta("3333333333333333333333333333333333333333333333333333333333333333");
        try {
            System.out.println(client.getRewardSolidity("TKryTFSUB2UY8jMVc3Rz3ofiUPrnR6pRAs"));
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
    }


    public void listWitnesses(){
        System.out.println("============= listWitnesses =============");
        TronClient client = TronClient.ofNile("3333333333333333333333333333333333333333333333333333333333333333");
        try {
            System.out.println(client.listWitnesses());
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
    }

    public void listExchanges(){
        System.out.println("============= listExchanges =============");
        TronClient client = TronClient.ofNile("3333333333333333333333333333333333333333333333333333333333333333");
        try {
            System.out.println(client.listExchanges());
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
    }

    /*public void voteWitness(){
        System.out.println("============= voteWitness =============");
        HashMap<String, String> witness = new HashMap<>();
        TronClient client = TronClient.ofNile("9fd7fb6311aa3251fca212ddf18a3e46a9956230939052e4df59b56f9de45dd5");
        try {
            witness.put("TG7RHXaL7E9rqSkBavX7s1vtikoz6np6bD","1");
            TransactionExtention transaction = client.voteWitness("TLtrDb1udekjDumnrf3EVeke3Q6pHkZxjm",witness);
            Transaction signedTxn = client.signTransaction(transaction);
            System.out.println(signedTxn.toString());
            TransactionReturn ret = client.broadcastTransaction(signedTxn);
            System.out.println("======== Result ========\n" + ret.toString());
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
    }*/

    public void voteWitness(){
        System.out.println("============= voteWitness =============");
        HashMap<String, String> witness = new HashMap<>();
        TronClient client = TronClient.ofNile("9fd7fb6311aa3251fca212ddf18a3e46a9956230939052e4df59b56f9de45dd5");
        try {
            witness.put("TG7RHXaL7E9rqSkBavX7s1vtikoz6np6bD","1");
            TransactionExtention transaction = client.voteWitness("TLtrDb1udekjDumnrf3EVeke3Q6pHkZxjm",witness);
            Transaction signedTxn = client.signTransaction(transaction);
            System.out.println(signedTxn.toString());
            TransactionReturn ret = client.broadcastTransaction(signedTxn);
            System.out.println("======== Result ========\n" + ret.toString());
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
    }

    /**
     * This is a trigger call - transfer trc-20 demo
     */
    public void triggerCallDemo() {
        TronClient client = TronClient.ofNile("3333333333333333333333333333333333333333333333333333333333333333");
        try {
            //function 'transfer'
            //params: function name, function params
            Function trc20Transfer = new Function("transfer",
                    Arrays.asList(new Address("TVjsyZ7fYF3qLF6BQgPmTEZy1xrNNyVAAA"),
                            new Uint256(BigInteger.valueOf(10L).multiply(BigInteger.valueOf(10).pow(18)))),
                    Arrays.asList(new TypeReference<Bool>() {}));

            //the params are: owner address, contract address, function
            TransactionBuilder builder = client.triggerCall("TJRabPrwbZy45sbavfcjinPJC18kjpRTv8", "TF17BgPaZYbz8oxbjhriubPDsA7ArKoLX3", trc20Transfer); //JST
            //set extra params
            builder.setFeeLimit(100000000L);
            builder.setMemo("Let's go!");
            //sign transaction
            Transaction signedTxn = client.signTransaction(builder.build());
            System.out.println(signedTxn.toString());
            //broadcast transaction
            TransactionReturn ret = client.broadcastTransaction(signedTxn);
            System.out.println("======== Result ========\n" + ret.toString());
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
    }

    public void generateAddress() {
        System.out.println("============= generateAddress =============");
        try {
            System.out.println(TronClient.generateAddress());
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
    }

    public void getBlockByLatestNum() {
        System.out.println("============= getBlockByLatestNum =============");
        TronClient client = TronClient.ofNile("3333333333333333333333333333333333333333333333333333333333333333");
        try {
            BlockListExtention blockListExtention = client.getBlockByLatestNum(100);
            System.out.println(blockListExtention);
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
    }

    public void getBlockByLimitNext() {
        System.out.println("============= getBlockByLimitNext =============");
        TronClient client = TronClient.ofNile("3333333333333333333333333333333333333333333333333333333333333333");
        try {
            BlockListExtention blockListExtention = client.getBlockByLimitNext(-11,0);
            System.out.println(blockListExtention);
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
    }

    public void getChainParameters() {
        System.out.println("============= getChainParameters =============");
        TronClient client = TronClient.ofNile("3333333333333333333333333333333333333333333333333333333333333333");
        try {
            System.out.println(client.getChainParameters());
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
    }

    public void getAssetIssueList() {
        System.out.println("============= getAssetIssueList =============");
        TronClient client = TronClient.ofNile("3333333333333333333333333333333333333333333333333333333333333333");
        try {
            System.out.println(client.getAssetIssueList());
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
    }

    public void getPaginatedAssetIssueList() {
        System.out.println("============= getPaginatedAssetIssueList =============");
        TronClient client = TronClient.ofNile("3333333333333333333333333333333333333333333333333333333333333333");
        try {
            System.out.println(client.getPaginatedAssetIssueList(0,20));
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
    }

    public void getAssetIssueByAccount() {
        System.out.println("============= getAssetIssueByAccount =============");
        TronClient client = TronClient.ofNile("3333333333333333333333333333333333333333333333333333333333333333");
        try {
            System.out.println(client.getAssetIssueByAccount("TMX73eFtUtyZXg62uCnjSywDu7pD5sau48"));
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
    }

    public void getAssetIssueById() {
        System.out.println("============= getAssetIssueById =============");
        TronClient client = TronClient.ofNile("3333333333333333333333333333333333333333333333333333333333333333");
        try {
            System.out.println(client.getAssetIssueById("1000134"));
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
    }

    public void listProposals() {
        System.out.println("============= listProposals =============");
        TronClient client = TronClient.ofNile("3333333333333333333333333333333333333333333333333333333333333333");
        try {
            System.out.println(client.listProposals());
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
    }

    //1-17
    public void getProposalById() {
        System.out.println("============= getProposalById =============");
        TronClient client = TronClient.ofMainnet("3333333333333333333333333333333333333333333333333333333333333333");
        try {
            System.out.println(client.getProposalById("15"));
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
    }

    public void getExchangeById() {
        System.out.println("============= getExchangeById =============");
        TronClient client = TronClient.ofMainnet("3333333333333333333333333333333333333333333333333333333333333333");
        try {
            System.out.println(client.getExchangeById("500"));
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
    }


    public static void main(String[] args) {
        App app = new App();
        // Trc20Demo trc20Demo = new Trc20Demo();
        SmartContractDemo smartContractDemo = new SmartContractDemo();

        // System.out.println(app.encodeFunctionCalling());

        // app.getAccountResource();
        // app.getAccountNet();
        //  app.getDelegatedResource();
//         app.getDelegatedResourceAccountIndex();
        // app.getChainParameters();
        // app.getAssetIssueList();
        // app.getPaginatedAssetIssueList();
        // app.getAssetIssueByAccount();
        // app.getAssetIssueById();
        // app.listProposals();
        // app.listWitnesses();
        // app.listExchanges();
        // app.getExchangeById();
        // app.getProposalById();
        // app.getAccount();
        // app.voteWitness();
        // app.updateAccount();
        // app.createAccount();
        // app.decodeFunctionReturn();
        // app.signTransaction();
        // app.trc20Encode();
        // app.sendTrx();
        // app.transferTrc10();
        // app.sendTrc20();
//         app.freezeBalance();
//         app.unFreezeBalance();
        // app.getBlockByNum();
        // app.getNowBlock();
        // app.getNodeInfo();
        // app.getBlockByLatestNum();
        // app.listNodes();
        // app.getTransactionInfoByBlockNum();
        // app.getTransactionInfoById();
        // app.getAccount();
        // app.voteWitness();
        // app.transferTrc20();
        
        // smartContractDemo.getSmartContract();
        // app.viewContractName();
        // app.triggerCallDemo();
        // app.getAccountSolidity();
        // app.getTransactionByIdSolidity();
        // app.generateAddress();
        // app.getNowBlockSolidity();
        // app.getRewardSolidity();

        // trc20Demo.getName();
        // trc20Demo.getSymbol();
        // trc20Demo.getDecimals();
        // trc20Demo.getTotalSupply();
        // trc20Demo.getBalanceOf();
        // trc20Demo.transfer();
        // trc20Demo.transferFrom();
        // trc20Demo.approve();
        // trc20Demo.getAllowance();
        // smartContractDemo.deploySmartContract();
    }
}