package cn.tedu.store.entity;

import java.util.Objects;

/**
 * 订单商品数据的实体类
 */
public class OrderItem extends BaseEntity {

	private static final long serialVersionUID = -8879247924788259070L;

	private Integer id;
	private Integer oid;
	private Integer pid;
	private String title;
	private String image;
	private Long price;
	private Integer num;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOid() {
		return oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	@Override
	public String toString() {
		return "OrderItem{" +
				"id=" + id +
				", oid=" + oid +
				", pid=" + pid +
				", title='" + title + '\'' +
				", image='" + image + '\'' +
				", price=" + price +
				", num=" + num +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		OrderItem orderItem = (OrderItem) o;
		return Objects.equals(id, orderItem.id) &&
				Objects.equals(oid, orderItem.oid) &&
				Objects.equals(pid, orderItem.pid) &&
				Objects.equals(title, orderItem.title) &&
				Objects.equals(image, orderItem.image) &&
				Objects.equals(price, orderItem.price) &&
				Objects.equals(num, orderItem.num);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, oid, pid, title, image, price, num);
	}
}
