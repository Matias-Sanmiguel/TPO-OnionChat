public static List<String> generarNombresDeNodos() {
    Scanner scanner = new Scanner(System.in);
    List<String> nodos = new ArrayList<>();

    System.out.print("Ingrese la cantidad de nodos: ");
    int cantidadNodos = scanner.nextInt();
    scanner.nextLine(); // Consumir salto de línea

    if (cantidadNodos < 2) {
        System.out.println("Debe haber al menos 2 nodos.");
        return Collections.emptyList();
    }

    for (int i = 0; i < cantidadNodos; i++) {
        // Genera nombres como A, B, ..., Z, AA, AB, ...
        String nombre = convertirIndiceANombre(i);
        nodos.add(nombre);
    }

    return nodos;

}

/**

 Convierte un número entero en una etiqueta tipo Excel: 0 → A, 1 → B, ..., 25 → Z, 26 → AA, etc.
 */
private static String convertirIndiceANombre(int indice) {
    StringBuilder nombre = new StringBuilder();
    while (indice >= 0) {
        nombre.insert(0, (char) ('A' + (indice % 26)));
        indice = indice / 26 - 1;
    }
    return nombre.toString();
}

void main() {
}