package dominio.unitaria;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import dominio.Prestamo;
import dominio.excepcion.PrestamoException;
import org.junit.Assert;
import org.junit.Test;

import dominio.Bibliotecario;
import dominio.Libro;
import dominio.repositorio.RepositorioLibro;
import dominio.repositorio.RepositorioPrestamo;
import testdatabuilder.LibroTestDataBuilder;

public class BibliotecarioTest {

	private static final String MARCOS="Marcos";
	@Test
	public void esPrestadoTest() {
		
		// arrange
		LibroTestDataBuilder libroTestDataBuilder = new LibroTestDataBuilder();
		
		Libro libro = libroTestDataBuilder.build(); 
		
		RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
		RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);
		
		when(repositorioPrestamo.obtenerLibroPrestadoPorIsbn(libro.getIsbn())).thenReturn(libro);
		
		Bibliotecario bibliotecario = new Bibliotecario(repositorioLibro, repositorioPrestamo);
		
		// act 
		boolean esPrestado =  bibliotecario.esPrestado(libro.getIsbn());
		
		//assert
		assertTrue(esPrestado);
	}

	@Test
	public void esPalindromoTest() {

		// arrange
		String isbn="1221";
		Libro libro = new LibroTestDataBuilder().conIsbn(isbn).build();

		RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
		RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);
		repositorioLibro.agregar(libro);
		Bibliotecario bibliotecario = new Bibliotecario(repositorioLibro, repositorioPrestamo);

		try {
			// act
			bibliotecario.prestar(libro.getIsbn(),MARCOS);

		} catch (PrestamoException e) {
			// assert
			assertEquals("Los libros políndromos solo se pueden utilizar en la biblitoeca", e.getMessage());
		}

	}

	@Test
	public void noEsPalindromoTest() {

		// arrange
		String isbn="1221";
		Libro libro = new LibroTestDataBuilder().conIsbn(isbn).build();


		RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
		RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);
		repositorioLibro.agregar(libro);
		Bibliotecario bibliotecario = new Bibliotecario(repositorioLibro, repositorioPrestamo);

		// act
		bibliotecario.prestar(libro.getIsbn(),MARCOS);

		//Assert
		assertTrue(bibliotecario.esPrestado(libro.getIsbn()));
		assertNotNull(repositorioPrestamo.obtenerLibroPrestadoPorIsbn(libro.getIsbn()));


	}
	
	@Test
	public void libroNoPrestadoTest() {
		
		// arrange
		LibroTestDataBuilder libroTestDataBuilder = new LibroTestDataBuilder();
		
		Libro libro = libroTestDataBuilder.build(); 
		
		RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
		RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);
		
		when(repositorioPrestamo.obtenerLibroPrestadoPorIsbn(libro.getIsbn())).thenReturn(null);
		
		Bibliotecario bibliotecario = new Bibliotecario(repositorioLibro, repositorioPrestamo);
		
		// act 
		boolean esPrestado =  bibliotecario.esPrestado(libro.getIsbn());
		
		//assert
		assertFalse(esPrestado);
	}

	@Test
	public void esMayTest(){
		// arrange
		String isbn="8g9f7t9";
		Libro libro = new LibroTestDataBuilder().conIsbn(isbn).build();

		RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
		RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);
		repositorioLibro.agregar(libro);
		Bibliotecario bibliotecario = new Bibliotecario(repositorioLibro, repositorioPrestamo);

		// act
		bibliotecario.prestar(libro.getIsbn(),MARCOS);

		// assert
		assertTrue(bibliotecario.esPrestado(libro.getIsbn()));
		assertNotNull(repositorioPrestamo.obtenerLibroPrestadoPorIsbn(libro.getIsbn()));
		assertNotNull(repositorioPrestamo.obtener(libro.getIsbn()).getFechaEntregaMaxima());

	}
}
