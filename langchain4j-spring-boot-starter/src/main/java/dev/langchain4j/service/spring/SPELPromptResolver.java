package dev.langchain4j.service.spring;

import dev.langchain4j.model.input.PromptTemplateCustomizer;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.EmbeddedValueResolver;
import org.springframework.core.env.Environment;

/**
 * support ${...} for environment and #{...} for beanFactory
 */
public class SPELPromptResolver implements PromptTemplateCustomizer {

    private Environment environment;
    private EmbeddedValueResolver embeddedValueResolver;

    public Environment getEnvironment() {
        if (environment == null) {
            environment = SpringStaticHolder.environment;
        }
        return environment;
    }

    public EmbeddedValueResolver getEmbeddedValueResolver() {
        if (embeddedValueResolver == null) {
            embeddedValueResolver = SpringStaticHolder.embeddedValueResolver;
        }
        return embeddedValueResolver;
    }

    @Override
    public String customer(String template) {
        return getEmbeddedValueResolver().resolveStringValue(getEnvironment().resolvePlaceholders(template));
    }

    static class SpringStaticHolder {

        private static Environment environment;
        private static EmbeddedValueResolver embeddedValueResolver;

        public SpringStaticHolder(Environment environment, ConfigurableBeanFactory beanFactory) {
            SpringStaticHolder.environment = environment;
            SpringStaticHolder.embeddedValueResolver = new EmbeddedValueResolver(beanFactory);

        }
    }
}
