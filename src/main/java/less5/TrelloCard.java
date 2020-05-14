package less5;

public class TrelloCard {
    private String name;
    private String id;
    private String key = TrelloKey.apiKey;
    private String token = TrelloKey.token;

    public String getKey() {
        return key;
    }

    public String getToken() {
        return token;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }
}
