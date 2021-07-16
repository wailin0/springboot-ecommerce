package com.gamingage.repository;

import java.util.List;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gamingage.model.CartItem;
import com.gamingage.model.ShoppingCart;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long>{





	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "delete from cart_item where id=?1 and shopping_cart_id=?2")
	void deleteByIdAndshoppingCartId(Long id, Long shopping_cart_id);

	List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);

	




}
