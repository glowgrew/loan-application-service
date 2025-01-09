# Шаблоны проектирования

В проекте можно выделить три основных шаблона проектирования из разных групп 
1. Поведенческие 
   2. Порождающие
3. Структурные

## Существующие шаблоны

### 1. Поведенческий шаблон: Наблюдатель (Observer Pattern)
https://refactoring.guru/ru/design-patterns/observer
- Реализован в системе уведомлений
- Примеры:
  ```java
  public interface NotificationService {
      boolean sendApplicationStatusUpdate(Application application, String status);
  }
  
  @Service
  public class NotificationServiceImpl implements NotificationService {
      @Override
      public boolean sendApplicationStatusUpdate(Application application, String status) {
          System.out.println("Notification sent to: " + application.getEmail());
          return true;
      }
  }
  ```
- Этот шаблон позволяет системе кредитных заявок уведомлять заинтересованные стороны (наблюдателей) об изменениях статуса без тесной связи между компонентами
- Когда статус заявки изменяется, сервис уведомлений "наблюдает" за этим изменением и реагирует, отправляя обновления соответствующим сторонам (в данном случае, по "электронной почте")

### 2. Порождающий шаблон: Фабричный метод (через Spring DI)
https://refactoring.guru/ru/design-patterns/factory-method
- Реализован через инъекцию (внедрение) зависимостей Spring в ApplicationController
- Примеры:
  ```java
  @RestController
  @RequiredArgsConstructor
  public class ApplicationController {
      private final ApplicationService applicationService;
      private final ApplicationRepository applicationRepository;
  }
  ```
- Кокретный фабричный метод: `BeanFactory#getBean` + можно явно указать `@Autowired`, но сейчас это стало опционально
- Spring DI действует как фабрика, храня, создавая и управляя инстансами сервисов и репозиториев
- Конструктор, создаваемый аннотацией из ломбока `@RequiredArgsConstructor`, является местом инъекции зависимостей в виде сервиса по управлению заявками на кредит и репозиторием заявок
- Это обеспечивает слабую связанность и упрощает тестирование приложения

### 3. Структурный шаблон: Фасад (Facade Pattern)
https://refactoring.guru/ru/design-patterns/facade
- Реализован в классе ApplicationServiceImpl
- Примеры:
  ```java
  @Service
  @RequiredArgsConstructor
  public class ApplicationServiceImpl implements ApplicationService {
      private final ApplicationRepository applicationRepository;
  
      @Override
      public ApplicationResultDTO processApplication(Application application) {
          application = applicationRepository.findById(application.getId()).orElseThrow();
          application.setStatus("APPROVED");
          applicationRepository.save(application);
          return new ApplicationResultDTO(true, "Approved by default");
      }
  }
  ```
- ApplicationService выступает как фасад, предоставляя упрощенный интерфейс к сложной подсистеме обработки кредитов
- Он инкапсулирует взаимодействие между различными компонентами (операции репозитория, обновления статуса, валидация) за чистым интерфейсом
- Клиентам (например, ApplicationController) не нужно знать о базовой сложности обработки кредитов

## Реализованные шаблоны

### 4. Поведенческий шаблон: Цепочка обязанностей (Chain of Responsibility)
https://refactoring.guru/ru/design-patterns/chain-of-responsibility
Реализован для валидации кредитных заявок.

- Примеры:
  ```java
  public abstract class ValidationHandler {
      protected ValidationHandler nextHandler;

      public ValidationHandler setNext(ValidationHandler handler) {
          this.nextHandler = handler;
          return handler;
      }

      public abstract ValidationResult validate(Application application);
  }

  public class AmountValidationHandler extends ValidationHandler {
      @Override
      public ValidationResult validate(Application application) {
          if (application.getRequestedAmount().compareTo(MIN_AMOUNT) < 0) {
              return new ValidationResult(false, "Amount is below minimum threshold");
          }
          return nextHandler != null ? nextHandler.validate(application) 
                                   : new ValidationResult(true, "Validation successful");
      }
  }
  ```
- Позволяет создавать цепочку валидаторов, каждый из которых отвечает за свою часть проверки заявки на кредит
- Легко добавлять новые правила валидации без изменения существующего кода
- Валидаторы могут быть переупорядочены или отключены при необходимости

### 5. Порождающий шаблон: Строитель (Builder)
https://refactoring.guru/ru/design-patterns/builder
Реализован для создания объектов заявок на кредит.

- Примеры:
  ```java
  public class ApplicationBuilder {
      private Long id;
      private String name;
      private String email;
      
      public ApplicationBuilder withName(String name) {
          this.name = name;
          return this;
      }
      
      public Application build() {
          return new Application(id, name, email, requestedAmount, status);
      }
  }

  // Использование
  Application application = new ApplicationBuilder()
      .withName(request.name())
      .withEmail(request.email())
      .withRequestedAmount(request.requestedAmount())
      .withStatus("PENDING")
      .build();
  ```
- Упрощает создание сложных объектов заявок
- Делает код более читаемым и поддерживаемым
- Позволяет создавать объекты пошагово

### 6. Структурный шаблон: Декоратор (Decorator)
https://refactoring.guru/ru/design-patterns/decorator
Реализован для добавления функционала логирования.

- Примеры:
  ```java
  @Slf4j
  @Component
  public class LoggingApplicationService implements ApplicationService {
      private final ApplicationService applicationService;
      
      @Override
      public ApplicationResultDTO processApplication(Application application) {
          log.info("Processing application for user: {}", application.getName());
          try {
              ApplicationResultDTO result = applicationService.processApplication(application);
              log.info("Application processed successfully. Result: {}", result);
              return result;
          } catch (Exception e) {
              log.error("Error processing application: {}", e.getMessage());
              throw e;
          }
      }
  }
  ```
- Добавляет функционал логирования без изменения основного кода сервиса
- Следует принципу открытости/закрытости (sOlid)
- Позволяет добавлять новые функции через обёртки