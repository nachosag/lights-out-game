package lightsOut;

public class CoordenadaRecomendada {
	private int fila;
	private int columna;

	CoordenadaRecomendada(int f, int c) {
		fila = f;
		columna = c;
	}

	public int getC() {
		return columna;
	}

	public int getF() {
		return fila;
	}

	public boolean equals(CoordenadaRecomendada otra) {
		if (this.fila == otra.getF() && this.columna == otra.getC()) {
			return true;
		}
		return false;
	}
}
