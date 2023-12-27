package es.albarregas.daofactory;


import es.albarregas.dao.ArticuloDAO;
import es.albarregas.dao.FotoDAO;
import es.albarregas.dao.IArticuloDAO;
import es.albarregas.dao.IFotoDAO;
import es.albarregas.dao.IUsuarioDAO;
import es.albarregas.dao.UsuarioDAO;


public class MySQLDAOFactory extends DAOFactory{

    
    @Override
    public IUsuarioDAO getUsuarioDAO() {
        return new UsuarioDAO();
    }

    @Override
    public IArticuloDAO getArticuloDAO() {
        return new ArticuloDAO();
    }

    @Override
    public IFotoDAO getFotoDAO() {
        return new FotoDAO();
    }

}
