package br.inatel.dm107.authentication;
import java.io.IOException;
import java.util.Base64;
import java.util.StringTokenizer;
import br.inatel.dm107.connection.Connection;

public class AuthenticationService {
public boolean authenticate(String credentials) {
        
        System.out.println(credentials);

        if (null == credentials)
            return false;
        // extraindo o valor vindo do header "Basic encodedstring" for Basic
        //Exemplo: "Basic YWRtaW46YWRtaW4="
        final String encodedUserPassword = credentials.replaceFirst("Basic"+ " ", "");
        String usernameAndPassword = null;
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(encodedUserPassword);
            usernameAndPassword = new String(decodedBytes, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        final String usernameAndPassSplit[] = usernameAndPassword.split(":");
        final String username = usernameAndPassSplit[0];
        final String password = usernameAndPassSplit[1];

        Connection con = new Connection();// Estamos usando um unico usuario e senha, aqui poderia ser feito via banco de dados
        
        
        boolean authenticationStatus = con.checkLogin(username,password);
        return authenticationStatus;
    }

}
