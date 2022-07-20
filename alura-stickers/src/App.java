import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class App {
    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        // Fazer uma conexão HTTP  e buscar os top 250 filmes
        String url = "https://api.nasa.gov/planetary/apod?api_key=gT4pLJ7bNdmR2cxEuy5LAdRQtO4eauL5YSzMgP70&start_date=2022-06-02&end_date=2022-07-02";
        
        ClienteHttp http = new ClienteHttp();
        String json = http.buscaDados(url);

        

    
        // Exibir e manipular os dados
        /* Para cada um dos filmes, pega a URL, pega o título e abre um input Stream
         *(uma corrente de bits) e a cada loop é criado um novo gerador de figurinhas
         */

         //Instância do new GeradorDeFigurinhas movido para fora do loop, para otimizar memória
        
        ExtratorDeConteudoDaNasa extrator = new ExtratorDeConteudoDaNasa();

        List<Conteudo> conteudos = extrator.extraiConteudos(json);

        GeradorDeFigurinhas geradora = new GeradorDeFigurinhas();

    for(int i = 0; i < 10; i++){

        
        Conteudo conteudo = conteudos.get(i); 

            InputStream inputStream = new URL(conteudo.getUrlImagem()).openStream();

            String nomeArquivo = "saida/" + conteudo.getTitulo() + ".png";

            geradora.cria(inputStream, nomeArquivo);

            System.out.println("Criando imagem " + conteudo.getTitulo());
           
            System.out.println();
        }
    }

}

