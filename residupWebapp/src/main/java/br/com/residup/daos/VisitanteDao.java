package br.com.residup.daos;
import br.com.residup.models.Morador;
import br.com.residup.models.Visitante;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static br.com.residup.shared.GerenciadorConexaoH2.abrirConexao;

public  class VisitanteDao {
	private static VisitanteDao instance;
	private Connection connection;
	public VisitanteDao() {
		this.handleOpenConnection();
	}
	private void handleOpenConnection() {
		try {
			this.connection = abrirConexao();

		} catch (ClassNotFoundException ex) {
			Logger.getLogger(MoradorDao.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SQLException ex) {
			Logger.getLogger(MoradorDao.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public static VisitanteDao getInstance() {
		if(instance == null) {
			instance = new VisitanteDao();
		}
		return instance;
	}

	public  boolean inserirVisitante(Visitante visitante) {
		String create = "INSERT INTO VISITANTE (NOME, SOBRENOME, DOCUMENTO, TELEFONE)\n" +
				"VALUES (?,?,?,?);";
		try {
			Connection connection = abrirConexao();
			PreparedStatement pst = connection.prepareStatement(create);
			pst.setString(1, visitante.getNome());
			pst.setString(2, visitante.getSobrenome());
			pst.setString(3, visitante.getDocumento());
			pst.setString(4, visitante.getFone());
			pst.executeUpdate();
			if (!connection.isClosed()){
				connection.close();
			}
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	public  boolean inserirRegistroVisitante(int idVisitante, int idMorador) {
		String create = "INSERT INTO REGISTRO_VISITANTE  (ID_MORADOR, ID_VISITANTE, DATA_REGISTRO)\n" +
				"VALUES (?,?,CURRENT_TIMESTAMP);";
		try {
			Connection connection = abrirConexao();
			PreparedStatement pst = connection.prepareStatement(create);
			pst.setInt(1, idMorador);
			pst.setInt(2, idVisitante);
			pst.executeUpdate();
			if (!connection.isClosed()){
				connection.close();
			}
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	public List<Visitante>  allVisitantes(String dataFiltro) {
		List<Visitante> visitantes = new ArrayList<>();
		LocalDate dataAtual = LocalDate.now();
		String resultado = (dataFiltro == null) ? dataAtual.toString() : dataFiltro;

		if (resultado.trim().isEmpty() || resultado.trim().isBlank()){
			resultado = dataAtual.toString();
		}
		String read = "SELECT RV.ID_REGISTRO,\tV.ID_VISITANTE, M.ID_MORADOR,  V.NOME, V.SOBRENOME, V.DOCUMENTO, V.TELEFONE, M.NUMERO_APARTAMENTO, M.BLOCO,  RV.CHECK_IN, RV.DATA_REGISTRO,\n" +
				"FROM REGISTRO_VISITANTE RV\n" +
				"INNER JOIN MORADOR M\n" +
				"ON M.ID_MORADOR = RV.ID_MORADOR\n" +
				"INNER JOIN VISITANTE V  \n" +
				"ON V.ID_VISITANTE = RV.ID_VISITANTE\n" +
				"WHERE DATA_REGISTRO ='" +resultado+ "';";

		try {
			Connection connection = abrirConexao();
			PreparedStatement pst = connection.prepareStatement(read);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				var v = new Visitante();
				String id = rs.getString("ID_VISITANTE");
				String nome = rs.getString("NOME");
				String sobreNome = rs.getString("SOBRENOME"  );
				String documento = rs.getString("DOCUMENTO");
				String fone = rs.getString("TELEFONE");
				String idRegistro =  rs.getString("ID_REGISTRO");
				String numeroApartamento = rs.getString("NUMERO_APARTAMENTO");
				String bloco = rs.getString("BLOCO");
				String checkIn = rs.getString("CHECK_IN");

				v.setNome(nome);
				v.setSobrenome(sobreNome);
				v.setDocumento(documento);
				v.setFone(fone);
				v.setId(id);

				v.setCheckIn(checkIn);
				v.setIdRegistro(idRegistro);
				var morador = new Morador();
				morador.setNumeroApartamento(numeroApartamento);
				morador.setBloco(bloco);
				v.setMorador(morador);
				visitantes.add(v);
			}
			if (!connection.isClosed()){
				connection.close();
			}
			return visitantes;
		} catch (Exception e) {
			System.out.println(e);
			return visitantes;
		}
	}


	public  ArrayList<Visitante> listarVisitantes(int idMorador, String filtroNome) {
		ArrayList<Visitante> visitantes = new ArrayList<>();
		String filtro ="";
		if (filtroNome != null ) {
			 filtro = (filtroNome.equals("")) ? "" : " AND (NOME LIKE '%" + filtroNome + "%' OR SOBRENOME LIKE '%" + filtroNome + "%')";
		}
		String read = "SELECT * FROM REGISTRO_VISITANTE RV\n" +
				"INNER JOIN VISITANTE V\n" +
				"ON V.ID_VISITANTE = RV.ID_VISITANTE \n" +
				"WHERE STATUS_CONTA = 1 AND ID_MORADOR = ? "+ filtro;



		try {
			Connection connection = abrirConexao();
			PreparedStatement pst = connection.prepareStatement(read);
			pst.setInt(1, idMorador);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				String id = rs.getString("ID_VISITANTE");
				String nome = rs.getString("NOME");
				String sobreNome = rs.getString("SOBRENOME"  );
				String documento = rs.getString("DOCUMENTO");
				String fone = rs.getString("TELEFONE");
				var v = new Visitante(nome,sobreNome,documento);
				v.setId(id);
				v.setFone(fone);
				visitantes.add(v);
			}
			if (!connection.isClosed()){
				connection.close();
			}
			return visitantes;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	public  void selecionarVisitante(Visitante visitante) {
		String read2 = "select * from visitante where ID_VISITANTE  = ?";
		try {
			Connection connection = abrirConexao();
			PreparedStatement pst = connection.prepareStatement(read2);
			pst.setString(1, visitante.getId());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				String id = rs.getString(1);
				String nome = rs.getString(2);
				String sobreNome = rs.getString(3);
				String documento = rs.getString(4);
			}
			if (!connection.isClosed()){
				connection.close();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}


	public  String getIdVisitante(String documento) {
		String read2 = "SELECT ID_VISITANTE FROM VISITANTE WHERE DOCUMENTO = ?";
		String busca = null;
		try {
			Connection connection = abrirConexao();
			PreparedStatement pst = connection.prepareStatement(read2);
			pst.setString(1, documento);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) busca = rs.getString("ID_VISITANTE");
			if (!connection.isClosed()){
				connection.close();
			}
			return busca;

		} catch (Exception e) {
			System.out.println(e);
			return busca;
		}
	}

	public  Boolean alterarVisitante(Visitante visitante) {
		String update = "update visitante set nome=?,sobrenome=?,documento=?, telefone =?  where ID_VISITANTE =?";
		try {
			Connection connection = abrirConexao();
			PreparedStatement pst = connection.prepareStatement(update);
			pst.setString(1, visitante.getNome());
			pst.setString(2, visitante.getSobrenome());
			pst.setString(3, visitante.getDocumento());
			pst.setString(4, visitante.getFone());
			pst.setString(5, visitante.getId());
			pst.executeUpdate();
			if (!connection.isClosed()){
				connection.close();
			}
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	public  Boolean fazerCheckInVisitante(int idRegistro) {
		String update = "UPDATE REGISTRO_VISITANTE SET CHECK_IN = 'S', DATA_CHECK_IN = CURRENT_TIMESTAMP WHERE ID_REGISTRO = ?";
		try {
			Connection connection = abrirConexao();
			PreparedStatement pst = connection.prepareStatement(update);
			pst.setInt(1, idRegistro);

			int resultSet = pst.executeUpdate();
			if (!connection.isClosed()){
				connection.close();
			}
			return resultSet == 1 ;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	public  boolean deletarVisitante(Visitante visitante) {
		String delete = "update visitante set STATUS_CONTA = 0  where ID_VISITANTE=?";
		try {
			Connection connection = abrirConexao();
			PreparedStatement pst = connection.prepareStatement(delete);
			pst.setString(1, visitante.getId());
			pst.executeUpdate();
			if (!connection.isClosed()){
				connection.close();
			}
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

}
