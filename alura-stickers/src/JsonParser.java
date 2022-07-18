import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonParser {
    //Atributos estáticos que são parte da inteligência da classe
    //1° Regex elimina a parte de itens e erro message e pega somente
    //a lista de filmes que desejamos recuperar
    //O regex é responsável por achar um padrão para quebrar filme a filme e assim separarmos
    private static final Pattern REGEX_ITEMS = Pattern.compile(".*\\[(.+)\\].*");
    //Essa segunda expressão regular irá pegar tudo oq estiver entre "" : "" dado os grupos de captura
    private static final Pattern REGEX_ATRIBUTOS_JSON = Pattern.compile("\"(.+?)\":\"(.*?)\"");

    public List<Map<String, String>> parse(String json) {

        Matcher matcher = REGEX_ITEMS.matcher(json);
        if (!matcher.find()) {

            throw new IllegalArgumentException("Não encontrou items.");
        }

        String[] items = matcher.group(1).split("\\},\\{");

        List<Map<String, String>> dados = new ArrayList<>();

        for (String item : items) {

            Map<String, String> atributosItem = new HashMap<>();

            Matcher matcherAtributosJson = REGEX_ATRIBUTOS_JSON.matcher(item);
            while (matcherAtributosJson.find()) {
                String atributo = matcherAtributosJson.group(1);
                String valor = matcherAtributosJson.group(2);
                atributosItem.put(atributo, valor);
            }

            dados.add(atributosItem);
        }

        return dados;
    }
}
