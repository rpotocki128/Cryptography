import java.io.*;
import java.util.*;

public class Cryptography {
    //Developed for my Intro to Cryptography Class
    //
    //NOTICE: In default VSCode the terminal cannot store all outputs, please use the file output to read the entire output for dictionary attack.
    //
    public static void main(String[] args) throws Exception {

        File inFile = null;
        FileWriter outFile = null;
        int i = 0,j = 0, k = 0, key1 = 0, key2 = 0, modIn = 0, lengthKey = 0, lengthText = 0, choice = 0;
        String str, fileStr, keyStr;
        char decodedStr[] = new char[512];
        Scanner scnr = new Scanner(System.in);

        do{
            System.out.println("\n\n\tPlease Select your Operation to Prefrom:");
            System.out.println("\t----------------------------------------");
            System.out.println("\tPress 1 to Brute Force Decrypt via Shift");
            System.out.println("\tPress 2 to Brute Force Decrypt via Affine");
            System.out.println("\tPress 3 to Decrypt Vigenere with Key");
            System.out.println("\tPress 4 to Decrypt Vigenere with Dictionary Attack");
            System.out.println("\tPress 5 to Encrypt via Affine");
            System.out.println("\tPress 6 to Exit");
            choice = scnr.nextInt();
            scnr.nextLine();

            switch(choice){
                case 1 : 
                    System.out.println("Enter Text to Decrypt:");   //mubsecujelyhjkqbshofjewhqfxo shift 10 = welcometovirtualcryptography
                    str = scnr.nextLine();
                    str = str.toUpperCase();


                    //Brute Force Shift Decrypter
                    for(i=0;i<26;i++){
                        System.out.println("Decrypted by a Shift of " + i);
                        for (j=0; j<(str.length()); j++) {
                            System.out.print((char)((str.charAt(j) -'A' + i)%26 + 'A'));
                        }
                        System.out.println("\n\n");
                    }
                break;
    
                case 2 ://Brute Force Decrypt via Affine
                    int[] aValues = new int[]{1,3,5,7,9,11,15,17,19,21,23,25};
                    System.out.println("Enter Text to Decrypt:"); //dfgsqfdfmebburmiuvfmzuomeoadgtzmeywudfeigcvsdmzudenngwocmdgurdzgxsimagsdgksudmebmwucvgzderduxmeourizavdgqzevfaosifeodfmmldmrxmxmsinuxmerenqgzudfc
                    str = scnr.nextLine();                        //A=15 B=4 == THOUGHTHEAFFINECIPHERISEASYTOBREAKWITHACOMPUTERITALLOWSMETOINTRODUCEYOUTOQUITEAFEWIMPORTANTIDEASINCRYPTOGRAPHYSUCHASTHEEXTENDEDEUCLIDEANALGORITHM
                    str = str.toUpperCase();

                    //For loop testing A
                    for(i = 0; i < aValues.length; i++){
                        modIn = 0;
                        switch(aValues[i]){
                            case 1:  modIn = 1; break;
                            case 3:  modIn = 9; break;
                            case 5:  modIn = 21; break;
                            case 7:  modIn = 15; break;
                            case 9:  modIn = 3; break;
                            case 11: modIn = 19; break;
                            case 15: modIn = 7; break;
                            case 17: modIn = 23; break;
                            case 19: modIn = 11; break;
                            case 21: modIn = 5; break;
                            case 23: modIn = 17; break;
                            case 25: modIn = 25; break;
                            default: System.out.println("Error Getting Modular Inverse Please Try Again"); break;
                        }
                        //For loop testing B
                        for(j = 0; j < 26; j++) {
                            System.out.println("\n\n\nTesting Key A=" + aValues[i]+" and B=" + j + "                             Mod-1=" + modIn);
                            //For loop running through characters
                            for(k = 0; k < str.length(); k++) {
                            /*
                            Uses formula of type 
                            A(y-B)=x -----> x%26 ----> answer
                            where A is the modular inverse of itself A(-1) 
                            eg 7 ---> 15     19 ----> 11    23 ----> 17

                            y is the current character being decoded
                                --> this is achieved by subtracting 'A' 
                                    to get characters in corresponding 
                                    numerical values based on english alphabet

                            B is the secondary key of which it can be any number
                            within the range of the alphabet. Subtract this value
                            from the current character and mutliple new value by A
                            to obtain x

                            Take x mod 26 to obtain new character value based on
                            numbers in alphabet

                            Check to see if negative, if so add 26 to return to alphabetic values
                            
                            Add 'A' back into the value to achieve a return to ASCII/UNICODE
                            translation

                            Solution*/
                            int x = (modIn * ((str.charAt(k)-'A')-j));
                            x = x%26;
                            if(x<0){x = x+26;}
                            x = x +'A';
                            System.out.print((char)x);
                            }
                        }
                    }
                    System.out.println("\n\n");
                break;

                case 3 ://Decrypt Vigenere with Key
                System.out.println("Enter Text to Decrypt:");
                    str = scnr.nextLine();
                    System.out.println("\nEnter the Key:");
                    keyStr = scnr.nextLine();
                    str.toUpperCase();
                    lengthText = str.length();
                    keyStr.toUpperCase();
                    lengthKey = keyStr.length();
                    for(i = 0; i < lengthText; i++)
                    {
                        decodedStr[i] = (char)(((((str.charAt(i) - 'A') - (keyStr.charAt(i%lengthKey) - 'A'))+26)%26)+'A');
                        System.out.print(decodedStr[i]); //writes the current post manipulation character to the terminal
                    }
                break;

                case 4 ://Dictionary Attack Vigenere from input.txt file with 1000 most common words in english
                    System.out.println("Enter Text to Decrypt:");
                    str = scnr.nextLine();

                    inFile = new File("C:/Users/rober/Desktop/input.txt"); //Enter you absolute file address here
                    outFile = new FileWriter("out.txt"); //Enter the File name you would like to output the full list of decoded messages to
                    Scanner filescnr = new Scanner(inFile);
                    while(filescnr.hasNextLine())
                    {
                        fileStr = filescnr.nextLine();
                        fileStr = fileStr.toUpperCase(); //Sets all letters in the input file to uppercase in order to correctly process them with ASCII manipulation
                        lengthKey = fileStr.length();
                        str = str.toUpperCase(); //Sets all letters in the given ciphertext to uppercase in order to correctly process them with ASCII manipulation
                        lengthText = str.length();
                        System.out.println("\n\nDecoded Using Keyword " + fileStr);
                        outFile.write("\n\nDecoded Using Keyword " + fileStr + "\n");
                        for(i = 0; i < lengthText; i++)
                        {
                            decodedStr[i] = (char)(((((str.charAt(i) - 'A') - (fileStr.charAt(i%lengthKey) - 'A'))+26)%26)+'A');
                            if(i%80==0) //Outputs a newline after every 80 characters to simulate historical terminal outputs and make the file easier to read
                            {
                                outFile.write("\n");
                            }
                            outFile.write(decodedStr[i]); // writes the current post manipulation character to the output file
                            System.out.print(decodedStr[i]); //writes the current post manipulation character to the terminal
                        }
                    }
                    outFile.close();
                break;

                case 5 ://Encrypt Via Affine
                    System.out.println("Enter Text to Encrypt:");
                    str = scnr.nextLine();
                    str = str.toUpperCase();
                    
                    System.out.println("Enter 1st Number in Key to Encrypt:");
                    key1 = scnr.nextInt();
                    scnr.nextLine();
                    System.out.println("Enter 2nd Number in Key to Encrypt:");
                    key2 = scnr.nextInt();
                    scnr.nextLine();

                    //Matches the inputted key with applicable modular inverses mod 26
                    switch(key1){
                        case 1:  modIn = 1; break;
                        case 3:  modIn = 9; break;
                        case 5:  modIn = 21; break;
                        case 7:  modIn = 15; break;
                        case 9:  modIn = 3; break;
                        case 11: modIn = 19; break;
                        case 15: modIn = 7; break;
                        case 17: modIn = 23; break;
                        case 19: modIn = 11; break;
                        case 21: modIn = 5; break;
                        case 23: modIn = 17; break;
                        case 25: modIn = 25; break;
                        default: System.out.println("Error Getting Modular Inverse Please Try Again"); break;
                    }

                    //Affine Encrypter
                    System.out.print("\n\n\nText Encrypted with Key " + key1 + " and " + key2 + "\n");
                    for(i=0; i < str.length(); i++){
                        int q = ((key1 * (str.charAt(i)-'A'))+key2);
                        q = q%26;
                        if(q<0){q = q+26;}
                        q = q +'A';
                        System.out.print((char)q);
                    }
                    System.out.println("\n\n");
                break;

                case 6 : System.out.println("\n\n\nGoodbye!\n\n\n");
                break;
                
                default : System.out.println("\nError Selecting Operation - Please Try Again\n");
                break;
            }
        }while (choice != 6);
    }
}
