package pl.training.commons.web;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public class UriBuilder {

    private static final String ID_PATH_SEGMENT = "/{id}";

    public static URI requestUriWithId(Object id) {
        return ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path(ID_PATH_SEGMENT)
                .buildAndExpand(id)
                .toUri();
    }

}
