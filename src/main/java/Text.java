public class Text {

    private boolean isFat;
    private boolean isUnderlined;
    private boolean isKursiv;

    public boolean isKursiv() {
        return isKursiv;
    }

    public void setKursiv(boolean kursiv) {
        isKursiv = kursiv;
    }

    private int orderedListItem;
    private boolean isUnorderedListItem;
    private String content="";

    public Text copyCharacteristics(Text text){
        Text reText = new Text();
        reText.setKursiv(text.isKursiv);
        reText.setFat(text.isFat);
        reText.setUnderlined(text.isUnderlined);
        reText.setContent(reText.getContent());
        reText.setOrderedListItem(reText.getOrderedListItem());
        reText.setUnorderedListItem(reText.isUnorderedListItem());
        return reText;
    }

    public Text (Text text){
        this.isKursiv=text.isKursiv();
        this.isFat=text.isFat();
        this.isUnderlined=text.isUnderlined();
        this.content=text.getContent();
        this.isUnorderedListItem=text.isUnorderedListItem();
        this.orderedListItem=text.getOrderedListItem();
    }

    public void print(){
        System.out.println("Content: " + content);
        System.out.println("isFat: " + isFat);
        System.out.println("isUnderlined: " + isUnderlined);
        System.out.println("isKursive: " + isKursiv);
        System.out.println("orderedListItem: " + orderedListItem);
        System.out.println("isUnorderedListItem: " + isUnorderedListItem);
        System.out.println("---------------------------------------");
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getOrderedListItem() {
        return orderedListItem;
    }

    public void setOrderedListItem(int orderedListItem) {
        this.orderedListItem = orderedListItem;
    }

    public Text(String str){

    }

    public Text(){

    }


    public boolean isFat() {
        return isFat;
    }

    public void setFat(boolean fat) {
        isFat = fat;
    }

    public boolean isUnderlined() {
        return isUnderlined;
    }

    public void setUnderlined(boolean underlined) {
        isUnderlined = underlined;
    }

    public boolean isUnorderedListItem() {
        return isUnorderedListItem;
    }

    public void setUnorderedListItem(boolean unorderedListItem) {
        isUnorderedListItem = unorderedListItem;
    }

}
