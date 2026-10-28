
package app.api;

import app.dao.RoutinesDAO;
import app.dto.RoutinesDTO;
import app.entities.Routines;
import io.javalin.Javalin;

public class RoutinesApi {

    private final RoutinesDAO routinesDAO;

    public RoutinesApi(RoutinesDAO routinesDAO) {
        this.routinesDAO = routinesDAO;
    }

    public void registerRoutes(Javalin app) {

        // GET all routines
        app.get("/routines", ctx -> {
            var routines = routinesDAO.getAll()
                    .stream()
                    .map(RoutinesDTO::new)
                    .toList();

            ctx.json(routines);
        });

        app.get("/routines/{id}", ctx -> {
            Long id = Long.parseLong(ctx.pathParam("id"));

            Routines routine = routinesDAO.getById(id);

            if (routine == null) {
                ctx.status(404);
                return;
            }

            ctx.json(new RoutinesDTO(routine));
        });

        app.post("/routines", ctx -> {
            Routines routine = ctx.bodyAsClass(Routines.class);

            routinesDAO.save(routine);

            ctx.status(201);
            ctx.json(new RoutinesDTO(routine));
        });

        app.put("/routines/{id}", ctx -> {
            Long id = Long.parseLong(ctx.pathParam("id"));

            Routines existingRoutine = routinesDAO.getById(id);

            if (existingRoutine == null) {
                ctx.status(404);
                return;
            }

            Routines updatedRoutine =
                    ctx.bodyAsClass(Routines.class);

            existingRoutine.setTitle(updatedRoutine.getTitle());
            existingRoutine.setDescription(
                    updatedRoutine.getDescription());
            existingRoutine.setStore_id(
                    updatedRoutine.getStore_id());

            Routines savedRoutine =
                    routinesDAO.update(existingRoutine);

            ctx.json(new RoutinesDTO(savedRoutine));
        });

        app.delete("/routines/{id}", ctx -> {
            Long id = Long.parseLong(ctx.pathParam("id"));

            Routines routine = routinesDAO.getById(id);

            if (routine == null) {
                ctx.status(404);
                return;
            }

            routinesDAO.delete(id);

            ctx.status(204);
        });
    }
}
