package it.unipi.dstm;

import com.ericsson.otp.erlang.OtpErlangDecodeException;
import com.ericsson.otp.erlang.OtpErlangExit;

import java.io.IOException;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {


        String region;

        System.out.println("Please Enter the Region: ");
        Scanner in = new Scanner(System.in);

        region= in.nextLine();
        if(region.isEmpty() || region==null)
        {
            region= "region1";
        }

        int numOfReceives;
        System.out.println("Please Enter the number of Receives: ");
         in = new Scanner(System.in);

        numOfReceives= in.nextInt();
        if(numOfReceives<=0)
        {
            numOfReceives= 50;
        }
        // start erlang client

        System.out.println("Working Directory = " + System.getProperty("user.dir"));
       String targetSystem= System.getProperty("os.name");
       String command="";
       if(targetSystem.contains("Windows"))
       {
           command="C:\\erl-24.3.3\\bin\\werl.exe -s earthquakeReadings start  -sname client@localhost -setcookie "+ region;

       }
       else
       {
           command="erl -s earthquakeReadings start  -sname client@localhost -setcookie "+ region; ;

       }

        try {
           Runtime.getRuntime().exec(command);
           System.out.println("The Number Generator Started");

        } catch (IOException e) {
            e.printStackTrace();
        }



        RSDataCollection rs=new RSDataCollection("server101@localhost",region,"servermailbox");
        try {
            rs.receiveData(numOfReceives);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OtpErlangDecodeException e) {
            e.printStackTrace();
        } catch (OtpErlangExit e) {
            e.printStackTrace();
        }
    }
}
