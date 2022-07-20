import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class GeradorDeFigurinhas {
    
    public void cria(InputStream inputStream, String nomeArquivo) throws Exception{
        // Leitura da imagem
        //Pega o arquivo localmente
        //InputStream inputStream = new FileInputStream(new File("entrada/filme.jpg"));
        //Pega o arquivo abrindo um stream para acessar a url do arquivo
        //InputStream inputStream = new URL("https://imersao-java-apis.s3.amazonaws.com/TopMovies_1.jpg").openStream();
        BufferedImage imagemOriginal = ImageIO.read(inputStream);

        // Criar nova imagem em memória com transparência e com tamanho novo
        // Cria uma nova imagem vazia com o fundo transparente
        int largura = imagemOriginal.getWidth();
        int altura = imagemOriginal.getHeight();
        int novaAltura = altura + 200;
        BufferedImage novaImagem = new BufferedImage(largura, novaAltura,BufferedImage.TRANSLUCENT);


        // Copiar a imagem original para nova imagem (em memória)
        // Graphics criado a partir da nova imagem
        Graphics2D graphics = (Graphics2D) novaImagem.getGraphics();


        // Escrevendo a antiga imagem dentro da nova, para aproveitar a transparência de imagem
        graphics.drawImage(imagemOriginal, 0, 0, null);

         // Configurar a fonte
         Font font = new Font(Font.SANS_SERIF, Font.BOLD, 64);
         graphics.setFont(font);
         graphics.setColor(Color.orange);


        // Escrever frase

        graphics.drawString("TOPZERA", 175, novaAltura - 100);
        
        // Escrever a nova imagem em um arquivo

        File directory = new File("saida/");
        if(!directory.exists()){
            directory.mkdir();
        }

        ImageIO.write(novaImagem, "png", new File((nomeArquivo)));
       
    }

}
