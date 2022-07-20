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
        String url = "https://api.nasa.gov/planetary/apod?api_key=gT4pLJ7bNdmR2cxEuy5LAdRQtO4eauL5YSzMgP70&start_date=2022-06-02&end_date=2022-07-02";
        URI endereco = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse <String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();

        // Extrair só os dados que interessam (título, poster e classificação)
        //Foi utilizado a classe JsonParser para introduzir expressões regulares(REGEX) afim de isolar os dados que interessam, via grupo de captura

        var parser = new JsonParser();
        List<Map<String, String>> listaDeConteudos = parser.parse(body);
      

    
        // Exibir e manipular os dados
        /* Para cada um dos filmes, pega a URL, pega o título e abre um input Stream
         *(uma corrente de bits) e a cada loop é criado um novo gerador de figurinhas
         */

         //Instância do new GeradorDeFigurinhas movido para fora do loop, para otimizar memória
        GeradorDeFigurinhas geradora = new GeradorDeFigurinhas();

    for(int i = 0; i < 50; i++){

        
        Map<String,String> conteudo = listaDeConteudos.get(i); 

            String urlImagem = 
            //conteudo.get("image")
            conteudo.get("url")
            .replaceAll("(@+)(.*).jpg$", "$1.jpg");

            String titulo = conteudo.get("title");

            InputStream inputStream = new URL(urlImagem).openStream();

            String nomeArquivo = "saida/" + titulo + ".png";

            geradora.cria(inputStream, nomeArquivo);

            System.out.println("Criando imagem " + titulo);
           
            System.out.println();
        }
    }

}

