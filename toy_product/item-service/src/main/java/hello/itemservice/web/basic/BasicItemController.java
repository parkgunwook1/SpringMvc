package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    /*
    *  상품 등록 폼   : GET  '/basic/items/add'
    * */
    @GetMapping("/add") // basic/items/add 로 클라이언트에게 url 요청이 오면 basic/addForm 보여주겠다.
    public String addForm() {
        return "basic/addForm";
    }
    /*
    *  상품 등록 처리 : POST '/basic/items/add'
    * */
//    @PostMapping("/add")
    public String addItem1(@RequestParam String itemName,
                       @RequestParam int price,
                       @RequestParam Integer quantity,
                       Model model) {

        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);

        model.addAttribute("item", item);

        return "basic/item";
    }

    @PostMapping("/add")
    public String addItem12(@ModelAttribute("item") Item item) {

//        @ModelAttribute => 아래의 객체를 자동으로 생성해준다.
//        즉, 클라이언트의 요청 파라미터를 처리해준다.
//        Item 객체를 생성하고, 요청 파라미터의 값을 프로퍼티 접근법(setXxx)으로 입력해준다.
//        Item item = new Item();
//        item.setItemName(itemName);
//        item.setPrice(price);
//        item.setQuantity(quantity);

        itemRepository.save(item);

//        @ModelAttribute = > model도 자동으로 생성해준다. 무엇으로? @ModelAttribute("item") => item 키값으로 저장해준다.
//        model.addAttribute("item", item);

        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItem3(@ModelAttribute Item item) {

//        model을 자동생성 해주는데 "item" 명을 넣어주지 않으면 Item 클래스를 첫글자를 item 으로 변경해주어서 생성해준다.
        itemRepository.save(item);

        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItem4(Item item) {

//        @ModelAttribute => 생략시 자동으로 생성해준다.
        itemRepository.save(item);

        return "basic/item";
    }
//    @PostMapping("/add")
    public String addItem5(Item item) {

        itemRepository.save(item);

        return "redirect:/basic/items/" + item.getId();
    }

    @PostMapping("/add")
    public String addItem6(Item item, RedirectAttributes redirectAttributes) {

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/basic/items/{itemId}" ;
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item);
        return "basic/editForm";

    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);

        return "redirect:/basic/items/{itemId}";

        /* 리다이렉트
        *  상품 수정은 마지막 뷰 템플릿을 호출하는 대신에 상품 상세 화면으로 이동하도록 리다이렉트를 호출한다.
        *  스프링은 redirect:/.. 으로 편리하게 리다이렉트 지원한다.
        *   @PathVariable의 값도 redirect 에도 사용할수 있다.
        * */

        /*
        * HTML FORM 전송은 PUT, PATCH를 지원하지 않고, GET, POST 만 사용할 수 있다.
        * PUT, PATCH는 HTTP API 전송시에 사용한다.
        *
        * */
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
