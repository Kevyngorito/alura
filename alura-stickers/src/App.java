import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception {

        // Fazer uma conexão HTTP  e buscar os top 250 filmes
        String url = "https://mocki.io/v1/9a7c1ca9-29b4-4eb3-8306-1adb9d159060";
        URI endereco = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse <String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();

        // Extrair só os dados que interessam (título, poster e classificação)
        //Foi utilizado a classe JsonParser para introduzir expressões regulares(REGEX) afim de isolar os dados que interessam, via grupo de captura

        var parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);
      

    
        // Exibir e manipular os dados

        for (Map<String,String> filme : listaDeFilmes) {
            System.out.println("\u001b[48;2;42;122;228m");
            System.out.println("\u001b[38;2;255;255;255m");
            System.out.println(filme.get("title"));
            //Reseta para o padrão (ESC)
            System.out.println("\u001b[m");

            String imdbRating = filme.get("imDbRating");
            Double imdbRatingDouble = Double.parseDouble(imdbRating);
            long roundeRating = Math.round(imdbRatingDouble);
            System.out.println(roundeRating);
            System.out.println(filme.get("image"));
            System.out.println(filme.get("imDbRating"));

            //Incrementa 1 estrelinha para cada i menor que o rating arredondado
            for(int i = 0; i < roundeRating; i++) {
                
                System.out.print("\u2b50");
            }

            System.out.println();
        }

    }
}
