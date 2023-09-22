public class SDESAttack_SameOrNotSame {

    private final String[] plaintext;
    private final String[] ciphertext;

    // 构造函数，接收明文和密文
    public SDESAttack_SameOrNotSame(String[] plaintext, String[] ciphertext) {
        this.plaintext = plaintext;
        this.ciphertext = ciphertext;
    }

    // 执行攻击，返回找到的密钥
    public void attack() {
        long before = System.currentTimeMillis();
        // 遍历所有可能的10位二进制密钥
        int num=0;
        String[] ans=new String[ciphertext.length];
        for (int i = 0; i < 1024; i++) {
            // 将当前的整数 i 转换为二进制，并填充到 10 位
            String binary = Integer.toBinaryString(i);
            while (binary.length() < 10) {
                binary = "0" + binary;
            }

            String key=binary;
            //System.out.println("---------第"+i+"轮数"+"---猜测密钥为："+key+"----------------");
            // 使用当前的密钥来尝试加密明文
            SDES.getkey(key);  
            for(int j=0;j<plaintext.length;j++){
                ans[j]=SDES.encrypt(plaintext[j], key);
            }
            //对比加密结果和真正的密文
            
            for(int k=0;k<ciphertext.length; k=k+1){
                if(ans[k].equals(ciphertext[k])){
                   num++;
                }
            }


            if(num==ciphertext.length){
                System.out.println("找到密文："+key);
                long later=System.currentTimeMillis();
                System.out.println("找到密文的总时间为："+(later-before)+"毫秒");
            }
            num=0;
        }
    }
    public static void main(String[] args) {
        String[] plainText={"00000001","00000010"};
        String[] cipherText={"10110101","01001011"};
        SDESAttack_SameOrNotSame sdesAttack2=new SDESAttack_SameOrNotSame(plainText, cipherText);
        sdesAttack2.attack();
    }   
}