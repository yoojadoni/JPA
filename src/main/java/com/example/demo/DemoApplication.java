package com.example.demo;

import com.example.demo.dto.MenuOptionDTO;
import com.example.demo.entity.Member;
import com.example.demo.entity.Orders;
import com.example.demo.entity.OrdersDetail;
import com.example.demo.entity.Shop;
import com.example.demo.entity.coupon.Coupon;
import com.example.demo.entity.coupon.DiscountTypeEnum;
import com.example.demo.entity.coupon.IssueCoupon;
import com.example.demo.entity.menu.Menu;
import com.example.demo.entity.owner.Owner;
import com.example.demo.entity.payment.PayStatusEnum;
import com.example.demo.entity.payment.Payment;
import com.example.demo.entity.payment.PaymentPK;
import com.example.demo.entity.payment.PgType;
import com.example.demo.repository.MenuRepository;
import com.example.demo.repository.OrdersDetailRepository;
import com.example.demo.repository.OrdersRepository;
import com.example.demo.repository.coupon.CouponRepository;
import com.example.demo.repository.coupon.IssueCouponQueryRepository;
import com.example.demo.repository.coupon.IssueCouponRepository;
import com.example.demo.repository.menuOption.MenuOptionRepository;
import com.example.demo.repository.payment.PaymentRepository;
import com.example.demo.service.MenuService;
import com.example.demo.service.OrderService;
import com.example.demo.service.coupon.CouponService;
import com.example.demo.service.coupon.IssueCouponService;
import com.example.demo.service.member.MemberService;
import com.example.demo.service.menuOption.MenuOptionService;
import com.example.demo.service.owner.OwnerService;
import com.example.demo.service.payment.PaymentService;
import com.example.demo.service.shop.ShopService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@EnableJpaAuditing // spring data jpa(Auditing 적용 BaseTimeEntity)
@EnableAspectJAutoProxy // AOP사용
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private OrderService orderService;

	@Autowired
	private MenuService menuService;

	@Autowired
	private OrdersRepository ordersRepository;

	@Autowired
	private OrdersDetailRepository ordersDetailRepository;

	@Autowired
	private ShopService shopService;

	@Autowired
	private OwnerService ownerService;

	@Autowired
	private MenuRepository menuRepository;

	@Autowired
	private MemberService memberService;

	@Autowired
	private IssueCouponRepository issueCouponRepository;

	@Autowired
	private CouponRepository couponRepository;

	@Autowired
	private IssueCouponService issueCouponService;

	@Autowired
	private IssueCouponQueryRepository issueCouponQueryRepository;

	@Autowired
	private CouponService couponService;

	@Autowired
	private MenuOptionService menuOptionService;

	@Autowired
	private MenuOptionRepository menuOptionRepository;

	@Autowired
	private PaymentService paymentService;

	@Autowired
	private PaymentRepository paymentRepository;

	/*@PostConstruct
	public void initDataCreate(){
		try {
			Member member = Member.builder()
					.name("멤버1")
					.addressCode("123-123")
					.addressDetail("서울시 마포구 마포동")
					.tel("010123123")
					.email("kks@kakao.com")
					.provider("kakao")
					.build();
			memberService.saveMember(member);

			Owner owner = Owner.builder()
					.id("owner_1")
					.password("asf")
					.tel("010123123")
					.name("돈까스 사장")
					.build();

			Owner ownerResult = ownerService.saveOwner(owner);

			Shop shop = Shop.builder()
					.name("돈까스")
					.minOrderPrice(10000)
					.bizNumber("1231231123")
					.owner(ownerResult)
					.addressCode("121-121")
					.addressDetail("서울시 어디어디어디ㅓ디")
					.build();

			Shop shopResult = null;
			shopResult = shopService.saveShop(shop);

			String menuname = "아메리카노";
			int price = 1500;
			Menu menu = Menu.builder()
					.menuName(menuname)
					.price(price)
					.shopId(shopResult)
					.build();

			menuService.save(menu);
			menuname = "라떼";
			price = 2500;
			Menu menu1 = Menu.builder()
					.menuName(menuname)
					.price(price)
					.shopId(shopResult)
					.build();
			menu1 = menuService.save(menu1);

			MenuOptionDTO.Request menuOptionDTO = MenuOptionDTO.Request
					.builder()
					.menu(menu1)
					.price(500L)
					.name("ice")
					.build();
			menuOptionService.saveMenuOptionFromDtoToResponse(menuOptionDTO);


//            Orders order = new Orders(4000L, 2);

			OrdersDetail ordersDetail = OrdersDetail.builder()
					.price(1500L)
					.amount(1)
					.menu(menu)
					.build();

			OrdersDetail ordersDetail2 = OrdersDetail.builder()
					.price(2500L)
					.amount(2)
					.menu(menu)
					.build();
			List<OrdersDetail> testList = new ArrayList<>();

			testList.add(ordersDetail);
			testList.add(ordersDetail2);

			Orders order = Orders.builder()
					.price(4000L)
					.quantity(2)
					.ordersDetails(testList)
					.build();

			order.addOrdersDetails(ordersDetail);
			order.addOrdersDetails(ordersDetail2);
			order = ordersRepository.save(order);

			Orders order1 = new Orders(4000L, 2);
			OrdersDetail ordersDetail3 = new OrdersDetail(1500L, 1, menu1);
			OrdersDetail ordersDetail4 = new OrdersDetail(2500L, 2, menu1);
			order1.addOrdersDetails(ordersDetail3);
			order1.addOrdersDetails(ordersDetail4);
			ordersRepository.save(order1);

			Orders order2 = new Orders(4000L, 2);
			OrdersDetail ordersDetail5 = new OrdersDetail(1500L, 1, menu);
			OrdersDetail ordersDetail6 = new OrdersDetail(2500L, 2, menu);
			order2.addOrdersDetails(ordersDetail5);
			order2.addOrdersDetails(ordersDetail6);
			ordersRepository.save(order2);

			Orders order3 = new Orders(4000L, 1);
			OrdersDetail ordersDetail7 = new OrdersDetail(1500L, 1, menu);
			order3.addOrdersDetails(ordersDetail7);
			ordersRepository.save(order3);

			Orders order4 = new Orders(4000L, 1);
			OrdersDetail ordersDetail8 = new OrdersDetail(1500L, 1, menu);
			order4.addOrdersDetails(ordersDetail8);
			ordersRepository.save(order4);

			Orders order5 = new Orders(4000L, 1);
			OrdersDetail ordersDetail9 = new OrdersDetail(1500L, 1, menu);
			order5.addOrdersDetails(ordersDetail9);
			ordersRepository.save(order5);

			Orders order6 = new Orders(4000L, 1);
			OrdersDetail ordersDetail10 = new OrdersDetail(1500L, 1, menu);
			order6.addOrdersDetails(ordersDetail10);
			ordersRepository.save(order6);


			Orders order7 = new Orders(4000L, 1);
			OrdersDetail ordersDetail11 = new OrdersDetail(1500L, 1, menu);
			order7.addOrdersDetails(ordersDetail11);
			ordersRepository.save(order7);

			Orders order8 = new Orders(4000L, 1);
			OrdersDetail ordersDetail12 = new OrdersDetail(1500L, 1, menu);
			order8.addOrdersDetails(ordersDetail12);
			ordersRepository.save(order8);

			Orders order9 = new Orders(4000L, 1);
			OrdersDetail ordersDetail13 = new OrdersDetail(1500L, 1, menu);
			order9.addOrdersDetails(ordersDetail13);
			ordersRepository.save(order9);


			Orders order10 = new Orders(4000L, 1);
			OrdersDetail ordersDetail14 = new OrdersDetail(1500L, 1, menu);
			order10.addOrdersDetails(ordersDetail14);
			ordersRepository.save(order10);

			Orders order11 = new Orders(4000L, 1);
			OrdersDetail ordersDetail15 = new OrdersDetail(1500L, 1, menu);
			order11.addOrdersDetails(ordersDetail15);
			ordersRepository.save(order11);

			Orders order12 = new Orders(4000L, 1);
			OrdersDetail ordersDetail16 = new OrdersDetail(1500L, 1, menu);
			order11.addOrdersDetails(ordersDetail16);
			ordersRepository.save(order11);

			Orders order13 = new Orders(4000L, 1);
			OrdersDetail ordersDetail17 = new OrdersDetail(1500L, 1, menu);
			order11.addOrdersDetails(ordersDetail17);
			ordersRepository.save(order11);

			PaymentPK paymentPK = new PaymentPK();
			paymentPK.setPayNumber("나이스1234");
			paymentPK.setPgType(PgType.NICE);

			Payment payment = Payment.builder()
					.paymentPK(paymentPK)
					.payStatus(PayStatusEnum.COMPLETE)
					.build();

			paymentService.savePaymentInfo(payment);

			Coupon coupon = Coupon.builder()
					.id("쿠폰번호1")
					.discountPrice(1000)
					.name("쿠폰 테스트")
					.discountType(DiscountTypeEnum.WON)
					.build();

			couponRepository.save(coupon);

			couponRepository.save(Coupon.builder()
					.id("쿠폰번호2")
					.discountPrice(10)
					.name("쿠폰 테스트")
					.discountType(DiscountTypeEnum.PERCENTAGE)
					.build());
			couponRepository.save(Coupon.builder()
					.id("쿠폰번호3")
					.discountPrice(10)
					.name("쿠폰 테스트")
					.discountType(DiscountTypeEnum.PERCENTAGE)
					.build()
			);

			IssueCoupon issueCoupon = IssueCoupon.builder()
					.coupon(coupon)
					.build();

			issueCouponRepository.save(issueCoupon);
		}catch (Exception e){

		}
	}*/
}
