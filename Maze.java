import java.io.*;
import java.nio.Buffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Stream;

public class Labirinto {
    private char[][] labirinto;

    public boolean percorreLabirinto() throws IllegalAccessException {
        if(Objects.isNull(labirinto)) throw new IllegalAccessException("");
        return percorreLabirinto(labirinto, 0 ,0);
    }
    
 private boolean percorreLabirinto(char[][] lab, int i, int j) {
        if(lab[i][j] == 'D') return true;
        lab[i][j] = 'O';
        
        boolean cima = false;
        boolean baixo = false;
        boolean direita = false;
        boolean esquerda = false;
        
        if(j+1 <= labirinto[i].length-1 && (labirinto[i][j+1] == ' ' || labirinto[i][j+1] == 'D')) {
            direita = percorreLabirinto(lab,i,j+1);
        }
        if(j-1 >= 0 && (labirinto[i][j-1] == ' ' || labirinto[i][j-1] == 'D')) {
            esquerda =  percorreLabirinto(lab,i,j-1);
        }
        if(i+1 <= labirinto.length-1 && (labirinto[i+1][j] == ' '|| labirinto[i+1][j] == 'D')) {
            cima = percorreLabirinto(lab,i+1,j);
        }
        if(i-1 >= 0 && (labirinto[i-1][j] == ' ' || labirinto[i-1][j] == 'D')) {
            baixo = percorreLabirinto(lab,i-1,j);
        }
        
        return esquerda || direita || cima || baixo;
    }
    
    public void criaLabirinto(String fileName) {
        long linhas = contarLinhas(fileName);

        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String data = reader.readLine();
            labirinto = new char[(int) linhas][data.toCharArray().length];

            int index = 0;
            while (Objects.nonNull(data)) {
                labirinto[index] = data.toCharArray();

                data = reader.readLine();
                index++;
            }
        } catch (IOException e) {
            System.out.println("Não foi possível achar o arquivo do labirinto");
            e.printStackTrace();
        }
    }

    private long contarLinhas(String fileName) {
        long lineCount = 0;
        try (Stream<String> stream = Files.lines(Path.of(fileName), StandardCharsets.UTF_8)) {
            lineCount = stream.count();
        } catch (IOException e) {
            System.out.println("Não foi possível achar o arquivo do labirinto");
            e.printStackTrace();
        }
        return lineCount;
    }

    public char[][] getLabirinto() {
        return labirinto;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < labirinto.length; i++) {
            stringBuilder.append(Arrays.toString(labirinto[i]));
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
