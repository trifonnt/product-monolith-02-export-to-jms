package name.trifon.product;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

public class JacksonFilter {

	@Retention(RetentionPolicy.RUNTIME)
	public static @interface AllUsers {
	}
	@Retention(RetentionPolicy.RUNTIME)
	public static @interface AdminOnly {
	}

	@JsonFilter("my-filter")
	@AllUsers
	public static class User {
		
		public final String email;

		@AdminOnly
		public final String details;

		public User(String email, String details) {
			this.email = email;
			this.details = details;
		}
	}

	public static class ShowAdminPropertiesFilter extends SimpleBeanPropertyFilter {

		@Override
		protected boolean include(BeanPropertyWriter writer) {
			return true; // deprecated since 2.3
		}

		@Override
		protected boolean include(PropertyWriter writer) {
			if (writer instanceof BeanPropertyWriter) {
				boolean beanContainsAllUsersAnnotation = ((BeanPropertyWriter) writer).getContextAnnotation(AllUsers.class) != null;
				boolean fieldContainsAdminAnnotation = ((BeanPropertyWriter) writer).getAnnotation(AdminOnly.class) != null;
				boolean fieldContainsAllUsersAnnotation = ((BeanPropertyWriter) writer).getAnnotation(AllUsers.class) != null;
				return beanContainsAllUsersAnnotation || fieldContainsAllUsersAnnotation || fieldContainsAdminAnnotation;
			}
			return true;
		}
	}
	public static class HideAdminPropertiesFilter extends SimpleBeanPropertyFilter {

		@Override
		protected boolean include(BeanPropertyWriter writer) {
			return true; // deprecated since 2.3
		}

		@Override
		protected boolean include(PropertyWriter writer) {
			if (writer instanceof BeanPropertyWriter) {
				boolean containsAdminAnnotation = ((BeanPropertyWriter) writer).getAnnotation(AdminOnly.class) != null;
				return !containsAdminAnnotation;
			}
			return true;
		}
	}

	public static void main(String[] args) throws JsonProcessingException {
		User user = new User("my-email", "me-secret-details");

		ObjectMapper userMapper = new ObjectMapper();
		userMapper.setFilterProvider(new SimpleFilterProvider().addFilter("my-filter", new HideAdminPropertiesFilter()));
		System.out.println("FIRST...: " + userMapper.writerWithDefaultPrettyPrinter().writeValueAsString(user));

		ObjectMapper adminMapper = new ObjectMapper();
		// shouldIncludeAllFields = true;
		adminMapper.setFilterProvider(new SimpleFilterProvider().addFilter("my-filter", new ShowAdminPropertiesFilter()));
		System.out.println("SECOND...: " + adminMapper.writerWithDefaultPrettyPrinter().writeValueAsString(user));
	}
}
