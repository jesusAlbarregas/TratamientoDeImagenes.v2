package es.albarregas.daofactory;

import es.albarregas.dao.IArticuloDAO;
import es.albarregas.dao.IFotoDAO;
import es.albarregas.dao.IUsuarioDAO;

public abstract class DAOFactory {

    /**
     * Objeto DAO de Usuario
     * @return interface de dicho objeto DAO
     */
    public abstract IUsuarioDAO getUsuarioDAO();
    /**
     * Objeto DAO de Articulo
     * @return interface de dicho objeto DAO
     */
    public abstract IArticuloDAO getArticuloDAO();
    /**
     * Objeto DAO de Foto
     * @return interface de dicho objeto DAO
     */
    public abstract IFotoDAO getFotoDAO();
    
    /**
     * Obtiene la fábrica concreta a la fuente de datos
     * @return la fábrica concreta
     */
    public static DAOFactory getDAOFactory() {
        
        DAOFactory daof = null;

        daof = new MySQLDAOFactory();

        return daof;
    }

}
