

//package modelos;

import java.util.Set;
import java.util.HashSet;

/**
 *
 * @author carmen_gordo
 */
public class Productos {
    public String id_producto;
    public String nombre;
    public String imagen;
    public TipoProducto tipo;
    public SubTipoRopaProducto subtipo_ropa;
    public SubTipoAccProducto subtipo_accesorios;
    public Set<TallaProducto> talla;
    public double precio;
    public int stock;
    public String id_tienda;
    public String id_almacen;	

    public Productos() {
        talla = new HashSet<>();
    }

    public Productos(String id_producto, String nombre, String imagen, TipoProducto tipo, SubTipoRopaProducto subtipo_ropa, SubTipoAccProducto subtipo_accesorios, Set<TallaProducto> talla, double precio, int stock, String id_tienda, String id_almacen) {
        this.id_producto = id_producto;
        this.nombre = nombre;
        this.imagen = imagen;
        this.tipo = tipo;
        this.subtipo_ropa = subtipo_ropa;
        this.subtipo_accesorios = subtipo_accesorios;
        this.talla = talla;
        this.precio = precio;
        this.stock = stock;
        this.id_tienda = id_tienda;
        this.id_almacen = id_almacen;
    }

    public String getId_producto() {
        return id_producto;
    }

    public void setId_producto(String id_producto) {
        this.id_producto = id_producto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public TipoProducto getTipo() {
        return tipo;
    }

    public void setTipo(TipoProducto tipo) {
        this.tipo = tipo;
        
        if (tipo != TipoProducto.Ropa) {
            this.subtipo_ropa = null;
        }
        if (tipo != TipoProducto.Accesorios) {
            this.subtipo_accesorios = null;
        }
    }

    public SubTipoRopaProducto getSubtipo_ropa() {
        return subtipo_ropa;
    }

    public void setSubtipo_ropa(SubTipoRopaProducto subtipo_ropa) {
        
        if (this.tipo == TipoProducto.Ropa) {
            this.subtipo_ropa = subtipo_ropa;
            
        } else {
            this.subtipo_ropa = null;
        }
    }

    public SubTipoAccProducto getSubtipo_accesorios() {
        return subtipo_accesorios;
    }

    public void setSubtipo_accesorios(SubTipoAccProducto subtipo_accesorios) {
        
        if (this.tipo == TipoProducto.Accesorios) {
            this.subtipo_accesorios = subtipo_accesorios;
            
        } else {
            this.subtipo_accesorios = null;
        }
    }

    public Set<TallaProducto> getTalla() {
        return talla;
    }

    public void setTalla(Set<TallaProducto> talla) {
        
        if (talla == null || talla.isEmpty()) {
            this.talla = null;
            
        } else {
            this.talla = talla;
        }
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getId_tienda() {
        return id_tienda;
    }

    public void setId_tienda(String id_tienda) {
        this.id_tienda = id_tienda;
    }

    public String getId_almacen() {
        return id_almacen;
    }

    public void setId_almacen(String id_almacen) {
        this.id_almacen = id_almacen;
    }
    
    
    //metodo para recoger subtipo independientemente
    public String recogerSubTipo() {
        if (tipo == TipoProducto.Ropa && subtipo_ropa != null) {
            return ponerLetraMayus(subtipo_ropa.name());
        } else if (tipo == TipoProducto.Accesorios && subtipo_accesorios != null) {
            return ponerLetraMayus(subtipo_accesorios.name());
        } else {
            return "-";
        }
    }

    private String ponerLetraMayus(String enumName) {
        if (enumName == null || enumName.isEmpty()) {
            return "";
        }
       
        return enumName.substring(0, 1).toUpperCase() + enumName.substring(1).toLowerCase();
    }
    
    public String recogerTallas() {
        if(talla == null){
            return "-";
        }
        
        if(!talla.isEmpty()) {
            StringBuilder tallasString = new StringBuilder();
            for (TallaProducto tallaProducto : talla) {
                tallasString.append(tallaProducto.name()).append(", ");
            }

            if (tallasString.length() > 0) {
                tallasString.delete(tallasString.length() - 2, tallasString.length());
            }

            return tallasString.toString();
        } else {
            return "-";
        }
    }
    
    
    //enum de tiendas:
    public enum TipoProducto {
        Accesorios,
        Ropa,
        Zapatillas
    }
    
    public enum SubTipoRopaProducto {
        Camiseta,
        Sudadera,
        Jersey,
        Chaqueta,
        Pantalon_corto,
        Pantalon_largo,
        Leggins,
        Chandals,
        Falda,
        Vestido
        
    }
    
    public enum SubTipoAccProducto {
        Gorro,
        Bolso,
        Mochila,
        Calcetines,
        Guantes,
        Cinturon,
        Cartera,
        Gafas_de_sol;
        
        public static SubTipoAccProducto fromString(String setSubtipo_accesorios) {
            switch (setSubtipo_accesorios) {
                case "Gorro": return Gorro;
                case "Bolso": return Bolso;
                case "Mochila": return Mochila;
                case "Calcetines": return Calcetines;
                case "Guantes": return Guantes;
                case "Cinturon": return Cinturon;
                case "Cartera": return Cartera;
                case "Gafas de sol": return Gafas_de_sol;
                default: throw new IllegalArgumentException("No se encontró el subtipo accesorio: " + setSubtipo_accesorios);
            }
        }
    }
    
    public enum TallaProducto {
        XS, S, M, L, XL, XXL, 
        T35, T36, T37, T38, T39, T40, T41, T42, T43, T44, T45;

        public static TallaProducto fromString(String talla) {
            
            switch (talla) {
                case "XS": return XS;
                case "S": return S;
                case "M": return M;
                case "L": return L;
                case "XL": return XL;
                case "XXL": return XXL;
                case "35": return T35;
                case "36": return T36;
                case "37": return T37;
                case "38": return T38;
                case "39": return T39;
                case "40": return T40;
                case "41": return T41;
                case "42": return T42;
                case "43": return T43;
                case "44": return T44;
                case "45": return T45;
                default: throw new IllegalArgumentException("No se encontró la talla: " + talla);
            }
        }
    }

}
