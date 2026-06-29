package lightsOut;

import java.util.LinkedList;
import java.util.Random;

public class Juego {
	private boolean[][] luces;
	private int tamañoMatriz;
	private int cantClicks;
	private int recordClicks;
	private LinkedList<CoordenadaRecomendada> listaRecomendaciones;

	public Juego() {
		iniciarVariables();
		iniciarListaRecomendaciones();
		iniciarCuadricula();
		mezclarCuadricula();
	}

	private void iniciarVariables() {
		tamañoMatriz = 4;
		cantClicks = 0;
		recordClicks = 0;
	}

	private void iniciarListaRecomendaciones() {
		listaRecomendaciones = new LinkedList<>();
	}

	public void reiniciarJuego() {
		reiniciarListaDeRecomendaciones();
		iniciarListaRecomendaciones();
		iniciarCuadricula();
		mezclarCuadricula();
		reiniciarCantClicks();
	}

	private void iniciarCuadricula() {
		luces = new boolean[tamañoMatriz][tamañoMatriz]; // Se inicializa la Matriz con todos los elementos en
															// false(luces apagadas)
	}

	private void mezclarCuadricula() {
		Random random = new Random();
		int f;
		int c;
		int mezclaAleatoria = 0;
		while (mezclaAleatoria < 5) { // Se realizan 5 toques aleatorios por la matriz para mezclar y cambiar los
										// estados de las luces
			f = random.nextInt(tamañoMatriz);
			c = random.nextInt(tamañoMatriz);
			CoordenadaRecomendada nuevaCoordenada = new CoordenadaRecomendada(f, c);
			if (!coordenadasRecomendadaContenida(nuevaCoordenada)) { // Verifica si la coordenada ya fue usada para
																		// cambiar el estado de su fila y su columna
																		// Si selecciona dos veces la misma coordenada
																		// deshace la accion de mezclar
				listaRecomendaciones.add(nuevaCoordenada); // Agrega el la coordenada a la lista para luego poder
															// reecomendarle al usuario donde apretar para deshacer
				alternarLuz(f, c);
				mezclaAleatoria++;
			}
		}
	}

	public void alternarLuz(int f, int c) {
		alternar(f, c);
		alternarFila(f);
		alternarColumna(c);
	}

	private void alternarFila(int f) {
		for (int c = 0; c < tamañoMatriz; c++) {
			alternar(f, c);
		}
	}

	private void alternarColumna(int c) {
		for (int f = 0; f < tamañoMatriz; f++) {
			alternar(f, c);
		}
	}

	private void alternar(int f, int c) {
		luces[f][c] = !luces[f][c];
	}

	// Cuando el usuario selecciona una luz de alguna de las coordenadas que se
	// encuentra en la listaRecomendaciones
	// entonces la elimina para no recomendarla accidentalmente
	public void actualizarRecomendaciones(int fila, int col) {
		CoordenadaRecomendada otraCoor = new CoordenadaRecomendada(fila, col);
		if (coordenadasRecomendadaContenida(otraCoor)) { // Si la coordenada esta contenida en la lista
			for (int i = 0; i < listaRecomendaciones.size(); i++) { // Busco cual es
				if (listaRecomendaciones.get(i).equals(otraCoor)) { // La encuentro
					listaRecomendaciones.remove(i); // La elimino
				}
			}
		} else { // Si no esta
			listaRecomendaciones.add(otraCoor); // La Agrego a la lista de recomendaciones para luego volver sobre la
												// marcha
		}
	}

	// Verifica si la coordenada ya fue utilizada
	private boolean coordenadasRecomendadaContenida(CoordenadaRecomendada coorNueva) {
		for (CoordenadaRecomendada coor : listaRecomendaciones) {
			if (coor.equals(coorNueva)) {
				return true;
			}
		}
		return false;
	}

	public boolean verificarVictoria() {
		for (int f = 0; f < tamañoMatriz; f++) {
			for (int c = 0; c < tamañoMatriz; c++) {
				if (luces[f][c]) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean consultarEstadoDeLuz(int f, int c) {
		return luces[f][c];
	}

	public void aumentarDificultad() {
		if (tamañoMatriz < 6) {
			tamañoMatriz++;
		}
		reiniciarJuego();
	}

	public void disminuirDificultad() {
		if (tamañoMatriz > 3) {
			tamañoMatriz--;
		}
		reiniciarJuego();
	}

	public void aumentarCantClicks() {
		cantClicks++;
	}

	public void reiniciarCantClicks() {
		cantClicks = 0;
	}

	public void reiniciarListaDeRecomendaciones() {
		listaRecomendaciones = null;
	}

	public void actualizarRecord() {
		if (recordClicks == 0) {
			recordClicks = cantClicks;
		}
		if (recordClicks > cantClicks) {
			recordClicks = cantClicks;
		}
	}

	public int getTamañoMatriz() {
		return tamañoMatriz;
	}

	public int getCantClicks() {
		return cantClicks;
	}

	public int getRecordClicks() {
		return recordClicks;
	}

	public CoordenadaRecomendada getUnaCoordenadaRecomendada() {
		Random random = new Random();
		return listaRecomendaciones.get(random.nextInt(listaRecomendaciones.size()));
	}

}
