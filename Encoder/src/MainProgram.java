import java.util.*;

public class MainProgram{

    //Using ASCII table to determine if all characters in plainText is part of the Reference Table (excluding whitespace)
    public static void verifyPT (String plainText){
        for(int i=0; i<plainText.length();i++)
        {
            if ((plainText.charAt(i) < '(' || plainText.charAt(i) > 'Z') && plainText.charAt(i) != ' ') 
            {
                System.out.println(plainText.charAt(i) + " is not allowed in this plaintext");
                System.exit(0);
            }
            else if (plainText.charAt(i) >= ':' && plainText.charAt(i) <= '@') 
            {
                System.out.println(plainText.charAt(i) + " is not allowed in this plaintext");
                System.exit(0);
            }
        } 
        return;
    }

    //Using ASCII table to determine if the offset character is part of the Reference Table
    public static void verifyOS (char offset){
        if (offset < '(' || offset > 'Z') 
        {
            System.out.println(offset + " is not a valid offset character");
            System.exit(0);
        }
        else if (offset >= ':' && offset <= '@') 
        {
            System.out.println(offset + " is not a valid offset character");
            System.exit(0);
        }
        return;
    }

    //Display the options available in the Menu
    public static void displayMenuSelections()
    {
        System.out.println("\nMenu Options");
        System.out.println("------------------------");
        System.out.println("Option 1: Encode the given text using the given offset character");
        System.out.println("Option 2: Decode the encoded text using the given offset character");
        System.out.println("Option 3: Exit the program");
        System.out.println("------------------------\n");
    }

    //Using offset character value, shifts each character in the string upwards
    public static String encode (String plainText) {
        char refTable[] = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','0','1','2','3','4','5','6','7','8','9','(',')','*','+',',','-','.','/'};
        int offset = 0;
        String encodedText = "";

        System.out.println("\nText before encoding: "+plainText);
        //Step 1: Read first character and identify offset value using the refTable
        for(int i=0;i<refTable.length;i++)
        {
            if(plainText.charAt(0) == refTable[i])
            {
                offset = i;
                encodedText += plainText.charAt(0);
                break;
            }
        }

        //Step 2: Read second character onwards, offset and append to new string
        System.out.println("Replacing characters with offset: "+ offset);

        //For each character in the string..
        for(int c=1;c<plainText.length();c++)
        {
            //find that character's index in the refTable
            for(int c_i=0;c_i<refTable.length;c_i++)
            {
                //upon successful match...
                if(plainText.charAt(c) == refTable[c_i])
                {
                    
                    int new_i = c_i - offset; 
                    
                    //If index is <0 , shift it back up to the end of refTable
                    if(new_i < 0)
                    {
                        new_i = new_i + 44;
                    }

                    //Append the character at the offsetted index of refTable
                    encodedText += refTable[new_i]; 
                }
            }
            //if it is unable to be found in the refTable (e.g. spacing inbetween HELLO WORLD)
            if (plainText.charAt(c) == ' ')
            {
                encodedText += plainText.charAt(c);
            }
        }

        return encodedText;
    }
    
    //Using offset character value, shifts each character in the string downwards
    public static String decode (String encodedText) {
        char refTable[] = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','0','1','2','3','4','5','6','7','8','9','(',')','*','+',',','-','.','/'};
        int offset = 0;
        String decodedText = "";
        
        System.out.println("\nEncoded text before decoding: "+encodedText);
        //Step 1: Read first character and identify offset value using the refTable
        for(int i=0;i<refTable.length;i++)
        {
            if(encodedText.charAt(0) == refTable[i])
            {
                offset = i;
                decodedText += encodedText.charAt(0);
                break;
            }
        }

        //Step 2: Read second character onwards, offset and append to new string
        System.out.println("Replacing characters with negative offset: "+ offset);


        //For each character in the string..
        for(int c=1;c<encodedText.length();c++)
        {
            //find that character's index in the refTable
            for(int c_i=0;c_i<refTable.length;c_i++)
            {
                //upon successful match...
                if(encodedText.charAt(c) == refTable[c_i])
                {
                    int new_i = c_i + offset; 
                    
                    //If index is >43 , return it to the front of refTable
                    if(new_i > 43)
                    {
                        new_i = new_i - 44;
                    }

                    //Append the character at the offsetted index of refTable
                    decodedText += refTable[new_i]; 
                }
            }
            //if it is unable to be found in the refTable (e.g. spacing inbetween HELLO WORLD)
            if (encodedText.charAt(c) == ' ')
            {
                decodedText += encodedText.charAt(c);
            }
        }
        return decodedText;
    }

    public static void main(String[] args) throws Exception {
        Scanner kb = new Scanner(System.in);
        String text;
        char offset;

        //Taking in valid plaintext string
        System.out.println("Please enter a text to encode. [Capitalized A-Z,0-9,()*+,-./ ONLY]");
        text = kb.nextLine();
        text = text.toUpperCase(); 
        verifyPT(text);

        //Taking in valid offset character
        System.out.println("Please enter an offset character [Capitalized A-Z,0-9,()*+,-./ ONLY]");
        offset = kb.nextLine().charAt(0);
        offset = Character.toUpperCase(offset);
        verifyOS(offset);

        //Append offset char to plaintext for encoding
        text = offset + text;
        System.out.println("\nProvided text: "+text); 

        /*
        *   Do switch-case with displayed menu
        *   Wasn't part of the requirements or logic but wasn't sure how the user wants the encoder to be implemented
        *   Thus decided to use switch-case
        */
        boolean exitSwitch = false;
        int selection;
        displayMenuSelections();
        do
        {
            try
            {
            System.out.println("Select Option (1/2/3): ");
                selection = kb.nextInt();
                switch(selection)
                {
                    case 1:
                        //Option 1: Encode the given text using the given offset character 
                        text = encode(text);
                        System.out.println("Encoded text: "+text+"\n");
                        break;
                    case 2:
                        //Option 2: Decode the encoded text using the given offset character
                        text = decode(text);
                        System.out.println("Decoded text: "+text+"\n");
                        break;
                    case 3:
                        //Option 3: Exit the program
                        System.out.println("Exiting program!");
                        exitSwitch = true;
                    default:
                        System.out.println("Out of range option entered, try again");
                }
            }
            catch (InputMismatchException ex)
            {
            System.out.println("Error occured - Value of invalid type has been input");
            return;
            }
        }while (!exitSwitch);
    }
}