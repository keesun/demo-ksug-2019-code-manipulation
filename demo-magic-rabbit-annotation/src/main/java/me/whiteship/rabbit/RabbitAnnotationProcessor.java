package me.whiteship.rabbit;

import com.google.auto.service.AutoService;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Set;

@AutoService(Processor.class)
public class RabbitAnnotationProcessor extends AbstractProcessor {

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Set.of(Rabbit.class.getName());
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(Rabbit.class);
        for (Element element : elements) {
            if (element.getSimpleName().contentEquals("Box")) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Processing a Box with Rabbit annotation.");

            } else {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Rabbit annotation can not be used for " + element.getSimpleName() +  ".");
            }
        }
        return true;
    }
}
