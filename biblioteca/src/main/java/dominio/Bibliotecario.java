package dominio;

import dominio.excepcion.PrestamoException;
import dominio.repositorio.RepositorioLibro;
import dominio.repositorio.RepositorioPrestamo;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Bibliotecario {

	public static final String EL_LIBRO_NO_SE_ENCUENTRA_DISPONIBLE = "El libro no se encuentra disponible";

	private RepositorioLibro repositorioLibro;
	private RepositorioPrestamo repositorioPrestamo;

	public Bibliotecario(RepositorioLibro repositorioLibro, RepositorioPrestamo repositorioPrestamo) {
		this.repositorioLibro = repositorioLibro;
		this.repositorioPrestamo = repositorioPrestamo;

	}



	public void prestar(String isbn) {
		if(esPrestado(isbn)) {
			throw new PrestamoException(EL_LIBRO_NO_SE_ENCUENTRA_DISPONIBLE);
		}

		if(esPolindromo(isbn)){
			throw new PrestamoException("Los libros políndromos solo se pueden utilizar en la biblitoeca");
		}

		Libro libro = this.repositorioLibro.obtenerPorIsbn(isbn);
		Prestamo prestamo = new Prestamo(libro);
		this.repositorioPrestamo.agregar(prestamo);

	}

	private boolean esPolindromo(String isbn){
		String invertido=new StringBuilder().reverse().toString();

		if(invertido.equals(isbn)){
			return true;
		}else{
			return false;
		}

	}
	public boolean esPrestado(String isbn) {

		Libro libro=this.repositorioPrestamo.obtenerLibroPrestadoPorIsbn(isbn);
		if(libro!=null){
			return true;
		}else {
			return false;
		}


	}

	public void prestarEnsayo(String isbn, String nombreUsuario) {
		if (esPrestado(isbn)) {

			throw new PrestamoException(EL_LIBRO_NO_SE_ENCUENTRA_DISPONIBLE);

		} else {

			if (esPolindromo(isbn)) {
				throw new PrestamoException("Los libros políndromos solo se pueden utilizar en la biblitoeca");
			} else {
				Libro libro = this.repositorioLibro.obtenerPorIsbn(isbn);
				Prestamo prestamo = new Prestamo(libro, nombreUsuario);
				this.repositorioPrestamo.agregar(prestamo);

			}

		}
	}

	public boolean esMay(String isbn){
		char[] cadena=isbn.toCharArray();
		int suma=0;
		for(int i=0;i<cadena.length;i++) {
			if(Character.isDigit(cadena[i])){
				suma+=Integer.parseInt(""+cadena[i]);
			}
		}
		if(suma>30){
			return true;
		}else {
			return false;
		}

	}

	public void prestarEnsayo2(String isbn, String nombreUsuario) {
		if (esPrestado(isbn)) {

			throw new PrestamoException(EL_LIBRO_NO_SE_ENCUENTRA_DISPONIBLE);

		} else {

			if (esPolindromo(isbn)) {
				throw new PrestamoException("Los libros políndromos solo se pueden utilizar en la biblitoeca");
			} else {
				Date fechaSolicitud=new Date();
				Date fechaEntregaMaxima= null;

				GregorianCalendar fechaCalendario = new GregorianCalendar();
				fechaCalendario.setTime(fechaSolicitud);
				int diaSemana = fechaCalendario.get(Calendar.DAY_OF_WEEK);

				if(esMay(isbn)){

				}
				Libro libro = this.repositorioLibro.obtenerPorIsbn(isbn);
				Prestamo prestamo = new Prestamo(fechaSolicitud,libro,fechaEntregaMaxima,nombreUsuario);
				this.repositorioPrestamo.agregar(prestamo);
			}

		}
	}



}
