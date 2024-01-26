package hello.itemservice.domain.item;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Item {

    private Long id;
    private String itemName;
    private Integer price;
    private Integer quantity;

    public Item() {
    }
    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
    // int는 null이 못들어가서 Integer로 사용했다.

    // 데이터가 왔다갔다하는 dto 에서는 @Data 사용가능
    // @Data를 쓸때는 주의할 필요가있다.
}
