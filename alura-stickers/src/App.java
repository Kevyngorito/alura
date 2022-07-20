import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {
    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        // Fazer uma conexão HTTP  e buscar os top 250 filmes
        String url = "https://api.mocki.io/v2/549a5d8b/Top250Movies";
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
        /* Para cada um dos filmes, pega a URL, pega o título e abre um input Stream
         *(uma corrente de bits) e a cada loop é criado um novo gerador de figurinhas
         */

         //Instância do new GeradorDeFigurinhas movido para fora do loop, para otimizar memória
        GeradorDeFigurinhas geradora = new GeradorDeFigurinhas();

        for (Map<String,String> filme : listaDeFilmes) {

            String urlImagem = filme.get("image");
            String titulo = filme.get("title");

            String nomeArquivo = titulo + ".png";

            InputStream inputStream = new URL(urlImagem).openStream();

            geradora.cria(inputStream, "saida/" + nomeArquivo);

            System.out.println("Criando imagem " + titulo);
           
            System.out.println();
        }

    }
}
