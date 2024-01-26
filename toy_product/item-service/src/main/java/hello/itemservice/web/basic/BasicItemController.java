package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/basic/items") // 클래스 레벨에서 이 클래스에 속한 모든 메서드는 basic/items 경로 매핑
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items",items);
        return "basic/items";
        // return 해당 메서드가 처리된 후에 클라이언트에게 전달할 뷰의 이름 지정
    }

    /**
    * 테스트용 데이터 추가
     * */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 10000,10));
        itemRepository.save(new Item("itemB", 20000,20));


    }

//    public BasicItemController(ItemRepository itemRepository){
//        this.itemRepository = itemRepository;
//    }
//      @RequiredArgsConstructor 가 생성자 하나일 때 만들어준다.

}
