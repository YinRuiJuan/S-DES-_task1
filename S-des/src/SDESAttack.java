public class SDESAttack {

    private final String[] plaintext;
    private final String[] ciphertext;

    // 构造函数，接收明文和密文
    public SDESAttack(String[] plaintext, String[] ciphertext) {
        this.plaintext = plaintext;
        this.ciphertext = ciphertext;
    }

    // 执行攻击，返回找到的密钥
    public String attack() {
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
            System.out.println("---------第"+i+"轮数"+"---猜测密钥为："+key+"----------------");
            // 使用当前的密钥来尝试加密明文
            SDES.getkey(key);  
            for(int j=0;j<plaintext.length;j++){
                ans[j]=SDES.encrypt(plaintext[j], key);
            }
            //对比加密结果和真正的密文
            
            for(int k=0;k<ciphertext.length; k=k+1){
                System.out.println("对于明文"+k+"   猜测密文："+ans[k]+"   真实密文："+ciphertext[k]);
                if(ans[k].equals(ciphertext[k])){
                   num++;
                }
            }


            if(num==ciphertext.length){
                System.out.println("找到密文："+key);
                long later=System.currentTimeMillis();
                System.out.println("找到密文的总时间为："+(later-before)+"毫秒");
                return key;
                
            }
            else{
                num=0;
            }
        }

        // 如果遍历所有可能的密钥都没有找到匹配的，那么返回 null
        return null;
    }
    public static void main(String[] args) {
        //已知真实的密钥是"1111100000",验证暴力破解
        
        String[] plaintext={"00000001","00000010","00000011","00000100","00000101","00000110","00000111","00001000","00001001","00001010","00001011","00001100","00001101","00001111","00010000","00010001","00010010","00010011","00010100"};
        String[] ciphertext={"10010001","00011011","11101010","10011011","11110100","10011110","01001011","01100100","11101011","00110101","11100000","00100001","01001010","10100101","01000001","01000110","00110000","01101101","00000100"};
        SDESAttack sdesAttack=new SDESAttack(plaintext, ciphertext);
        System.out.println(sdesAttack.attack());
 
    }   
}