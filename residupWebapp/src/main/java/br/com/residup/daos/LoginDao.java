package br.com.residup.daos;

import br.com.residup.models.Morador;
import org.jasypt.util.password.StrongPasswordEncryptor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static br.com.residup.shared.GerenciadorConexaoH2.abrirConexao;

public class LoginDao {
    private static LoginDao instance;
    private Connection connection;

    private LoginDao() {
        this.handleOpenConnection();
    }

    private void handleOpenConnection() {
        try {
            this.connection = abrirConexao();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LoginDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(LoginDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static LoginDao getInstance() {
        if (instance == null) {
            instance = new LoginDao();
        }
        return instance;
    }


    public boolean logar(Morador morador) {
        StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
        boolean retorno = false;

        try (Connection connection = abrirConexao();
             PreparedStatement instrucao = connection.prepareStatement("SELECT * FROM MORADOR WHERE CPF = ?")
        ) {
            instrucao.setString(1, morador.getCpf());

            try (ResultSet rs = instrucao.executeQuery()) {
                if (rs.next() && passwordEncryptor.checkPassword(morador.getSenhaDeAcesso(), rs.getString("SENHA_ACESSO"))) {
                    System.out.println("Morador encontrado!");
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao executar a consulta: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            System.out.println("Erro ao carregar o driver do banco de dados: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    public boolean autualizarSenha(Morador morador) {
        StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
        boolean retorno = false;

        try (Connection connection = abrirConexao();
             PreparedStatement instrucao = connection.prepareStatement("UPDATE MORADOR SET SENHA_ACESSO = ?, DATA_ALTERACAO= CURRENT_TIMESTAMP WHERE  CPF = ? ")
        ) {
            instrucao.setString(1,passwordEncryptor.encryptPassword(morador.getSenhaDeAcesso()));
            instrucao.setString(2, morador.getCpf());
            int linhasRetorno = instrucao.executeUpdate();
            connection.close();
            return linhasRetorno > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao update senha: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            System.out.println("Erro ao carregar o driver do banco de dados: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public String recuperarId(String cpf) {
        try (Connection connection = abrirConexao();
             PreparedStatement instrucao = connection.prepareStatement("SELECT * FROM MORADOR WHERE CPF = ?")) {
            instrucao.setString(1, cpf);
            System.out.println(cpf);

            ResultSet rs = instrucao.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            } else {
                return "nulo";
            }
        } catch (SQLException e) {
            System.out.println("Erro ao executar a consulta: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            System.out.println("Erro ao carregar o driver do banco de dados: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }



    public boolean validaPrimeiroAcesso(String cpf) {
        StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
        try (Connection connection = abrirConexao();
             PreparedStatement instrucao = connection.prepareStatement("SELECT SENHA_ACESSO FROM MORADOR WHERE CPF = ?")) {
            instrucao.setString(1, cpf);

            try (ResultSet rs = instrucao.executeQuery()) {
                if (rs.next()) {
                    String senhaAcesso = rs.getString("SENHA_ACESSO");
                    boolean senhaCorreta = passwordEncryptor.checkPassword("senha123", senhaAcesso);
                    return senhaCorreta;
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao executar a consulta: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            System.out.println("Erro ao carregar o driver do banco de dados: " + e.getMessage());
            throw new RuntimeException(e);
        }

    }
}






